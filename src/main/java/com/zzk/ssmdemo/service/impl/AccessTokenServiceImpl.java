package com.zzk.ssmdemo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzk.ssmdemo.beans.WeiXin;
import com.zzk.ssmdemo.service.AccessTokenService;
import com.zzk.ssmdemo.utils.PureNetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

/**
 * @ClassName AccessTokenServiceImpl
 * @Description: 获取微信公众号的access_token
 * @Author situliang
 * @Date 2019/7/14
 * @Version V1.0
 **/

@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    private static final Logger log = LoggerFactory.getLogger(AccessTokenServiceImpl.class);

    @Autowired
    private RedisTemplate redisCacheTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public String getAccessToken() {
        String token = (String) redisCacheTemplate.opsForValue().get(PREFIX);
        if (token == null || "".equals(token)) {
            // redis中不存在access_token,从微信服务器获取新的token
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" +
                    WeiXin.getAppid() + "&secret=" + WeiXin.getAppsecret();
            String response = PureNetUtil.get(url);
            try {
                Map<String, String> map = mapper.readValue(response, Map.class);
                if (map.containsKey("access_token")) {
                    token = map.get("access_token");
                    redisCacheTemplate.opsForValue().set(PREFIX, token, Duration.ofMinutes(100));
                } else {
                    token = "";
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        } else {
            return token;
        }
        log.info("获取的token:{}", token);
        return token;
    }

}
