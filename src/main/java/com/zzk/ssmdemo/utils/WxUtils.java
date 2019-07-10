package com.zzk.ssmdemo.utils;

import com.thoughtworks.xstream.XStream;
import com.zzk.ssmdemo.beans.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName WxUtils
 * @Description: 微信的工具类
 * @Author situliang
 * @Date 2019/7/9
 * @Version V1.0
 **/

public class WxUtils {

    private static final Logger log = LoggerFactory.getLogger(WxUtils.class);

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
        Map<String, String> map = new HashMap<>();
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
     * 用户处理所有事件和消息的回复
     *
     * @param requestMap 请求参数的map
     * @return 返回用户的xml消息
     */
    public static String getResponse(Map<String, String> requestMap) {
        BaseMessage msg = null;
        String msgType = requestMap.get("MsgType");
        switch (msgType) {
            case "text":
                msg = delTextMessage(requestMap);
                break;
            case "image":
                break;
            case "voice":
                break;
            case "video":
                break;
            case "shortvideo":
                break;
            case "location":
                break;
            case "link":
                break;
            default:
                break;
        }
        //把消息对象处理为xml数据包
        if (msg != null) {
            return beanToXml(msg);
        } else {
            return null;
        }
    }

    /**
     * 处理文本消息
     *
     * @param requestMap 请求消息的map对象
     * @return 文本消息对象
     */
    private static BaseMessage delTextMessage(Map<String, String> requestMap) {
        TextMessage tm = new TextMessage(requestMap, "处理成功");
        return tm;
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
