package com.zzk.ssmdemo.beans;

import com.thoughtworks.xstream.XStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WxTest
 * @Description: 测试消息对象
 * @Author situliang
 * @Date 2019/7/10
 * @Version V1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class WxTest {

    @Test
    public void testMsg(){
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("ToUserName", "to");
        requestMap.put("FromUserName", "from");
        TextMessage textMessage = new TextMessage(requestMap, "还好");

        //设置需要处理XStream注释的类
        XStream stream = new XStream();
        stream.processAnnotations(TextMessage.class);
        stream.processAnnotations(ImageMessage.class);
        stream.processAnnotations(MusicMessage.class);
        stream.processAnnotations(NewsMessage.class);
        stream.processAnnotations(VoiceMessage.class);
        stream.processAnnotations(VideoMessage.class);
        String xml = stream.toXML(textMessage);
        System.out.println(xml);
    }

}
