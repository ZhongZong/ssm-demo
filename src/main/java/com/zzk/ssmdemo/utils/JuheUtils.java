package com.zzk.ssmdemo.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName JuheUtils
 * @Description: 聚合各种API的方法
 * @Author situliang
 * @Date 2019/7/10
 * @Version V1.0
 **/

public class JuheUtils {

    private static final String API_KEY = "4dadcd44e79e174e8fcefb28e6c9499a";

    private static final Logger log = LoggerFactory.getLogger(JuheUtils.class);

    /**
     * 获取返回数据中的一个属性示例,此处以获取今日温度为例
     * "temperature": "8℃~20℃"     今日温度
     *
     * @param city 城市名
     * @return 消息
     */
    public static String GetTodayTemperatureByCity(String city) {
        ObjectMapper mapper = new ObjectMapper();
        // 此处以返回json格式数据示例,所以format=2,以根据城市名称为例,cityName传入中文
        String url = "http://v.juhe.cn/weather/index?cityname=" + city + "&key=" + API_KEY;
        String result = PureNetUtil.get(url);
        String weather;
        Map<String, Object> map = new HashMap();
        if (result != null) {
            try {
                map = mapper.readValue(result, Map.class);
                String code = (String) map.get("resultcode");
                log.info("解析天气后的结果:{}", map.toString());
                String successCode = "200";
                if (code != null && successCode.equals(code)) {
                    //查询天气成功
                    Map<String, Object> resultMap = ((Map<String, Object>) map.get("result"));
                    Map<String, String> skMap = (Map<String, String>) resultMap.get("sk");
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("当前温度:" + skMap.get("temp") + "°C").append("\n");
                    stringBuilder.append("当前风向:" + skMap.get("wind_direction")).append("\n");
                    stringBuilder.append("当前风力:" + skMap.get("wind_strength")).append("\n");
                    stringBuilder.append("当前湿度:" + skMap.get("humidity")).append("\n");
                    stringBuilder.append("天气最近更新时间:" + skMap.get("time")).append("\n");
                    return stringBuilder.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
                weather = "查询天气失败,请确认城市名是否正确!";
                return weather;
            }
        }
        weather = (String) map.get("reason");
        return weather;
    }

    /**
     * 利用思知机器人的api https://www.ownthink.com/
     *
     * @param key 内容
     * @return 机器人回复的内容
     */
    public static String chat(String key) {
        ObjectMapper mapper = new ObjectMapper();
        //如果传入的是空格
        if (key == null || "".equals(key.trim())) {
            key = "?";
        }
        String url =
                "https://api.ownthink.com/bot?spoken=" + key;
        String result = PureNetUtil.get(url);
        String error = "主人暂时去火星了,稍后就回来了!";
        try {
            Map map = mapper.readValue(result, Map.class);
            String message = (String) map.get("message");
            if (message != null && "success".equals(message)) {
                Map<String, Object> dataMap = (Map<String, Object>) map.get("data");
                Map<String, Object> infoMap = (Map<String, Object>) dataMap.get("info");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((String) infoMap.get("text"));
                //String text = "回复的消息是:\n" + ;
                return stringBuilder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return error;
        }
        return error;
    }
}
