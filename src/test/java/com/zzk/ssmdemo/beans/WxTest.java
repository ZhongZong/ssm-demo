package com.zzk.ssmdemo.beans;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import com.zzk.ssmdemo.utils.JuheUtils;
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
    public void testMsg() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("ToUserName", "to");
        requestMap.put("FromUserName", "from");
        TextMessage textMessage = new TextMessage(requestMap, "还好");

        //设置需要处理XStream注释的类
        XStream stream = new XStream();
        stream.processAnnotations(TextMessage.class);
        /*stream.processAnnotations(ImageMessage.class);
        stream.processAnnotations(MusicMessage.class);
        stream.processAnnotations(NewsMessage.class);
        stream.processAnnotations(VoiceMessage.class);
        stream.processAnnotations(VideoMessage.class);*/
        String xml = stream.toXML(textMessage);
        System.out.println(xml);
    }

    @Test
    public void testWeather() {
        String re = JuheUtils.GetTodayTemperatureByCity("深圳");
        System.out.println(re);
    }

    @Test
    public void testChat() {
        String re = JuheUtils.chat("你有女朋友吗?");
        System.out.println(re);
    }


    @Test
    public void testButton() {
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
        ObjectMapper mapper = new ObjectMapper();
        try {
            String value = mapper.writeValueAsString(button);
            System.out.println(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
