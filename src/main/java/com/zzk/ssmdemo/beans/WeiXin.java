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

    private static String baiduAppId;

    private static String baiduAppKey;

    private static String baiduSecretKey;

    private static String bdImageDetectAppId;

    private static String bdImageDetectAppKey;

    private static String bdImageDetectSecretKey;

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

    public static String getBaiduAppId() {
        return baiduAppId;
    }

    @Value("${weixin.config.baidu-image-word-appid}")
    public void setBaiduAppId(String baiduAppId) {
        WeiXin.baiduAppId = baiduAppId;
    }

    public static String getBaiduAppKey() {
        return baiduAppKey;
    }

    @Value("${weixin.config.baidu-image-word-appkey}")
    public void setBaiduAppKey(String baiduAppKey) {
        WeiXin.baiduAppKey = baiduAppKey;
    }

    public static String getBaiduSecretKey() {
        return baiduSecretKey;
    }

    @Value("${weixin.config.baidu-image-word-secretkey}")
    public void setBaiduSecretKey(String baiduSecretKey) {
        WeiXin.baiduSecretKey = baiduSecretKey;
    }

    public static String getBdImageDetectAppId() {
        return bdImageDetectAppId;
    }

    @Value("${weixin.config.baidu-image-detect-appid}")
    public void setBdImageDetectAppId(String bdImageDetectAppId) {
        WeiXin.bdImageDetectAppId = bdImageDetectAppId;
    }

    public static String getBdImageDetectAppKey() {
        return bdImageDetectAppKey;
    }

    @Value("${weixin.config.baidu-image-detect-apikey}")
    public void setBdImageDetectAppKey(String bdImageDetectAppKey) {
        WeiXin.bdImageDetectAppKey = bdImageDetectAppKey;
    }

    public static String getBdImageDetectSecretKey() {
        return bdImageDetectSecretKey;
    }

    @Value("${weixin.config.baidu-image-detect-secretkey}")
    public void setBdImageDetectSecretKey(String bdImageDetectSecretKey) {
        WeiXin.bdImageDetectSecretKey = bdImageDetectSecretKey;
    }
}

