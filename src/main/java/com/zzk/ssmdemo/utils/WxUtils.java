package com.zzk.ssmdemo.utils;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.baidu.aip.ocr.AipOcr;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thoughtworks.xstream.XStream;
import com.zzk.ssmdemo.beans.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @ClassName WxUtils
 * @Description: 微信的工具类
 * @Author situliang
 * @Date 2019/7/9
 * @Version V1.0
 **/

public class WxUtils {

    private static final Logger log = LoggerFactory.getLogger(WxUtils.class);

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 验证签名
     *
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param signature 微信加密签名
     * @return 是否成功
     */
    public static boolean check(String timestamp, String nonce, String signature) {
        // 1）将token、timestamp、nonce三个参数进行字典序排序
        String[] strs = new String[]{timestamp, nonce, WeiXin.getToken()};
        Arrays.sort(strs);
        // 2）将三个参数字符串拼接成一个字符串进行sha1加密
        String str = strs[0] + strs[1] + strs[2];
        String mysig = DigestUtils.sha1Hex(str);
        // 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        return mysig.equals(signature);
    }

    /**
     * 对微信推送过来的消息流进行解析（xml->map）
     *
     * @param inputStream 消息流
     * @return 解析好的map对象
     */
    public static Map<String, String> parseRequest(InputStream inputStream) {
        Map<String, String> map = Maps.newHashMap();
        SAXReader reader = new SAXReader();
        try {
            // 读取输入流,获取文档对象
            Document document = reader.read(inputStream);
            // 根据文档对象获取根节点
            Element root = document.getRootElement();
            // 获取根节点的所有子节点
            List<Element> elements = root.elements();
            for (Element element : elements) {
                map.put(element.getName(), element.getStringValue());
            }

        } catch (Exception e) {
            log.error("将消息输入流解析成map对象出现异常:{}", e.getMessage());
        }
        return map;
    }

    /**
     * 处理文本消息
     *
     * @param requestMap 请求消息的map对象
     * @return 文本消息对象
     */
    public static String delTextMessage(Map<String, String> requestMap) {
        String content = requestMap.get("Content");
        String reply;
        if (content.contains("天气")) {
            //天气查询消息
            reply = JuheUtils.GetTodayTemperatureByCity(content.replace("天气", ""));
        } else if ("图文".equals(content)) {
            //返回图文消息
            reply = delTuWen(requestMap);
            return reply;
        } else {
            //聊天机器人消息
            reply = JuheUtils.chat(content);
        }
        TextMessage tm = new TextMessage(requestMap, reply);
        return beanToXml(tm);
    }

    public static String delTuWen(Map<String, String> requestMap) {
        List<Article> articles = Lists.newArrayList();
        Article article = new Article("这是图文消息的标题", "这是图文消息的介绍",
                "https://mat1.gtimg.com/pingjs/ext2020/qqindex2018/dist/img/qq_logo_2x.png", "https://www.qq.com/");
        articles.add(article);
        BaseMessage message = new NewsMessage(requestMap, articles);
        return beanToXml(message);
    }

    /**
     * 处理用户的订阅事件
     *
     * @param requestMap 请求的消息
     * @return 返回的xml消息
     */
    public static String delSubscribe(Map<String, String> requestMap) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("欢迎关注微信公众号").append("\n");
        TextMessage textMessage = new TextMessage(requestMap, stringBuilder.toString());
        return beanToXml(textMessage);
    }

    /**
     * 处理click菜单点击事件
     *
     * @param requestMap 请求参数
     * @return 返回给用户的xml消息
     */
    public static String delClick(Map<String, String> requestMap) {
        String key = requestMap.get("EventKey");
        switch (key) {
            case "1":
                // 点击了一级菜单
                return beanToXml(new TextMessage(requestMap, "你点击了第一个一级菜单"));
            case "32":
                // 点击了第三个一级菜单的第二个子菜单
                return beanToXml(new TextMessage(requestMap,
                        "你点击了第三个一级菜单的第二个子菜单"));
            default:
                break;
        }
        return null;
    }


    /**
     * 进行图片识别,识别图片中的文字
     * <p>
     * {
     * "log_id": 2471272194,
     * "words_result_num": 2,
     * "words_result":
     * [
     * {"words": " TSINGTAO"},
     * {"words": "青島睥酒"}
     * ]
     * }
     *
     * @param requestMap 请求参数
     * @return 返回的xml数据
     */
    public static String delImage(Map<String, String> requestMap) {
        // 设置图片地址
        String path = requestMap.get("PicUrl");
        // 调用接口
        //本地图片识别
        // JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        // 网络图片识别
        String res = BdSdkUtil.basicGeneralUrl(path);
        log.info("百度AI识别图片文字结果:{}", res);
        StringBuilder sb = new StringBuilder();
        try {
            Map<String, Object> resMap = mapper.readValue(res, Map.class);
            List<Map<String, String>> wordsResult = (List<Map<String, String>>) resMap.get("words_result");
            for (Map<String, String> entry : wordsResult) {
                sb.append(entry.get("words")).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return beanToXml(new TextMessage(requestMap, sb.toString()));
    }

    /**
     * 图片识别, 识别图片是否违规
     *
     * @param requestMap 请求参数
     * @return xml格式的消息
     */
    public static String delImageDetect(Map<String, String> requestMap) {
        String url = requestMap.get("PicUrl");
        //String path = "C:\\Users\\situliang\\Desktop\\2.png";
        // 调用接口
        String res = BdSdkUtil.imageCensorUserDefined(url);
        log.info("百度AI审核图片结果:{}", res);
        StringBuilder sb = new StringBuilder();
        try {
            Map<String, Object> resMap = mapper.readValue(res, Map.class);
            if (requestMap.containsKey("error_msg")) {
                //调用接口出现错误
                return beanToXml(new TextMessage(requestMap, requestMap.get("error_msg")));
            }
            sb.append("图片审核结果:").append(resMap.get("conclusion")).append("\n");
            if (resMap.containsKey("data")) {
                sb.append("图片分析结果如下:").append("\n");
                List<Map<String, Object>> datas = (List<Map<String, Object>>) resMap.get("data");
                for (Map<String, Object> entry : datas) {
                    sb.append(entry.get("msg")).append("->");
                    if (entry.containsKey("probability")) {
                        sb.append("相似度:").append(entry.get("probability"));
                    }
                    if (entry.containsKey("stars")) {
                        List<Map<String, String>> stars = (List<Map<String, String>>) entry.get("stars");
                        for (Map star : stars) {
                            sb.append("名称:").append(star.get("name")).append(",");
                            sb.append("相似度:").append(star.get("probability")).append("。");
                        }
                    }
                    sb.append("\n");
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return beanToXml(new TextMessage(requestMap, sb.toString()));
    }

    /**
     * 把消息对象转换成xml数据包
     *
     * @param message 消息对象
     * @return xml格式的字符串
     */
    private static String beanToXml(BaseMessage message) {
        //设置需要处理XStream注释的类
        XStream stream = new XStream();
        stream.processAnnotations(TextMessage.class);
        stream.processAnnotations(ImageMessage.class);
        stream.processAnnotations(MusicMessage.class);
        stream.processAnnotations(NewsMessage.class);
        stream.processAnnotations(VoiceMessage.class);
        stream.processAnnotations(VideoMessage.class);
        String xml = stream.toXML(message);
        return xml;
    }

}
