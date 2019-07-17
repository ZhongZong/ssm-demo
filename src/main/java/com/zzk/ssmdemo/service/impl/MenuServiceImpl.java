package com.zzk.ssmdemo.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzk.ssmdemo.beans.*;
import com.zzk.ssmdemo.service.AccessTokenService;
import com.zzk.ssmdemo.service.MenuService;
import com.zzk.ssmdemo.utils.PureNetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName MenuServiceImpl
 * @Description: 菜单接口实现类
 * @Author situliang
 * @Date 2019/7/15
 * @Version V1.0
 **/

@Service
public class MenuServiceImpl implements MenuService {

    private static final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Autowired
    private AccessTokenService accessTokenService;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void addMenu() {
        Button button = new Button();
        // 第一个一级菜单
        button.getButton().add(new ClickButton("一级点击", "1"));
        // 第二个一个菜单
        button.getButton().add(new ViewButton("一级跳转", "http://www.qq.com"));
        // 第三个一个菜单弹出子菜单
        SubButton subButton = new SubButton("有子菜单");
        // 第一个子菜单
        subButton.getSub_button().add(new PhotoOrAlbumButton("传图", "31"));
        // 第二个子菜单
        subButton.getSub_button().add(new ClickButton("点击", "32"));
        // 第三个子菜单
        subButton.getSub_button().add(new ViewButton("百度", "http://www.baidu.com"));
        button.getButton().add(subButton);
        try {
            String value = mapper.writeValueAsString(button);
            String url = " https://api.weixin.qq.com/cgi-bin/menu/create?access_token="
                    + accessTokenService.getAccessToken();
            String result = PureNetUtil.postJson(url, value);
            log.info("微信公众号添加自定义菜单后的返回值:{}", result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setIndustry() {
        String url = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token="
                + accessTokenService.getAccessToken();
        String data = "{\n" +
                "    \"industry_id1\":\"1\",\n" +
                "    \"industry_id2\":\"2\"\n" +
                "}";
        String result = PureNetUtil.postJson(url, data);
        log.info("设置微信公众号所属行业:{},返回结果:{}", data, result);
    }

    @Override
    public String getIndustry() {
        String url = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token="
                + accessTokenService.getAccessToken();
        String result = PureNetUtil.get(url);
        return result;
    }
}
