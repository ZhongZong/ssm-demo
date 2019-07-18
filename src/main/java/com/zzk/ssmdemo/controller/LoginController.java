package com.zzk.ssmdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzk.ssmdemo.beans.WeiXin;
import com.zzk.ssmdemo.utils.PureNetUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * @ClassName LoginController
 * @Description: 用户通过网页授权登录
 * @Author situliang
 * @Date 2019/7/18
 * @Version V1.0
 **/

@RequestMapping("/login")
@RestController
public class LoginController {

    private ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/getuserinfo")
    public String getUserInfo(@RequestParam("code") String code, @RequestParam("state") String state) {
        // 获取access_token的地址
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WeiXin.getAppid()
                + "&secret=" + WeiXin.getAppsecret()
                + "&code=" + code + "&grant_type=authorization_code";
        String res = PureNetUtil.get(url);
        String userInfo = "";
        try {
            Map<String, String> accessMap = mapper.readValue(res, Map.class);
            String accessToken = accessMap.get("access_token");
            String openid = accessMap.get("openid");
            // 拉取用户的基本信息
            String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="
                    + accessToken + "&openid=" + openid + "&lang=zh_CN";
            userInfo = PureNetUtil.get(infoUrl);
            Map<String, String> userInfoMap = mapper.readValue(userInfo, Map.class);
            String img = userInfoMap.get("headimgurl");
            System.out.println("用户头像地址:" + img);
            System.out.println(userInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        String req = code + "----" + state;
        return "getuserinfo:" + userInfo;
    }
}
