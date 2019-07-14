package com.zzk.ssmdemo.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName WeiXin
 * @Description: 微信的配置类
 * @Author situliang
 * @Date 2019/7/9
 * @Version V1.0
 **/

@Component
@ConfigurationProperties(prefix = "weixin.config")
public class WeiXin {

    private static String appid;

    private static String appsecret;

    private static String token;

    private static String weatherKey;


    @Value("${weixin.config.appid}")
    public void setAppid(String appid) {
        WeiXin.appid = appid;
    }

    public static String getAppid() {
        return appid;
    }


    @Value("${weixin.config.appsecret}")
    public void setAppsecret(String appsecret) {
        WeiXin.appsecret = appsecret;
    }

    public static String getAppsecret() {
        return appsecret;
    }

    @Value("${weixin.config.token}")
    public void setToken(String token) {
        WeiXin.token = token;
    }

    public static String getToken() {
        return token;
    }



    @Value("${weixin.config.weather-key}")
    public void setWeatherKey(String weatherKey) {
        WeiXin.weatherKey = weatherKey;
    }

    public static String getWeatherKey() {
        return weatherKey;
    }

}

