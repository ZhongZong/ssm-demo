package com.zzk.ssmdemo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzk.ssmdemo.entity.User;
import com.zzk.ssmdemo.service.AccessTokenService;
import com.zzk.ssmdemo.service.TemplateService;
import com.zzk.ssmdemo.service.UserService;
import com.zzk.ssmdemo.utils.PureNetUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName TemplateServiceImpl
 * @Description: 发送模版消息实现类
 * @Author situliang
 * @Date 2019/7/17
 * @Version V1.0
 **/

@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private UserService userService;

    @Autowired
    private AccessTokenService accessTokenService;

    private ObjectMapper mapper = new ObjectMapper();

    private static final Logger log = LoggerFactory.getLogger(TemplateServiceImpl.class);

    @Override
    public String sendTemplateMessage() {
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
                + accessTokenService.getAccessToken();
        String data = "";
        List<User> users = userService.getUsers();
        if (!CollectionUtils.isEmpty(users)) {
            for (User user : users) {
                data = "{\"touser\":\""+user.getOpenid()+"\",\"template_id\":\"UIadmQtoL4XBitJLHxMOL568Rq0AmSL8fJFTBVGnCck\",\"data\":{\"first\":{\"value\":\"你有新的反馈信息！\",\"color\":\"#173177\"},\"company\":{\"value\":\"司徒凉的公司\",\"color\":\"#173177\"},\"time\":{\"value\":\"2019年7月17日\",\"color\":\"#173177\"},\"result\":{\"value\":\"面试通过\",\"color\":\"#173177\"},\"remark\":{\"value\":\"请和我公司的HR联系\",\"color\":\"#173177\"}}}";
                String res = PureNetUtil.postJson(url, data);
                log.info("发送给:{}的模版消息结果是:{}", user.getOpenid(), res);
            }
        }
        return null;
    }
}
