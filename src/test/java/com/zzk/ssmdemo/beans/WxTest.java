package com.zzk.ssmdemo.beans;

import com.baidu.aip.ocr.AipOcr;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import com.zzk.ssmdemo.service.AccessTokenService;
import com.zzk.ssmdemo.service.MenuService;
import com.zzk.ssmdemo.utils.BdSdkUtil;
import com.zzk.ssmdemo.utils.JuheUtils;
import com.zzk.ssmdemo.utils.PureNetUtil;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private MenuService menuService;

    @Autowired
    private AccessTokenService accessTokenService;

    private ObjectMapper mapper = new ObjectMapper();

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

    @Test
    public void testPic() throws Exception {
        String APP_ID = WeiXin.getBaiduAppId();
        String API_KEY = WeiXin.getBaiduAppKey();
        String SECRET_KEY = WeiXin.getBaiduSecretKey();
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        //System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // 调用接口
        String path = "C:\\Users\\situliang\\Desktop\\2.png";
        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> resMap = mapper.readValue(res.toString(), Map.class);
            List<Map<String, String>> wordsResult = (List<Map<String, String>>) resMap.get("words_result");
            for (Map<String, String> entry : wordsResult) {
                System.out.println(entry.get("words"));
            }
//            System.out.println(wordsResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(res.toString());

    }

    @Test
    public void picDetect() throws Exception {
        String path = "http://www.xinhuanet.com/world/2018-07/24/1123166562_15323820122421n.jpg";
        String res = BdSdkUtil.imageCensorUserDefined(path);
        System.out.println(res);
    }

    @Test
    public void setIndustry() {
        menuService.setIndustry();
    }

    @Test
    public void getIndustry() {
        String re = menuService.getIndustry();
        System.out.println(re);
    }

    @Test
    public void testUpload() {
        //新增临时素材的url
        String url = " https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
        url = url.replace("ACCESS_TOKEN", accessTokenService.getAccessToken())
                .replace("TYPE", "image");
        String path = "C:\\Users\\situliang\\Desktop\\2.png";
        String res = PureNetUtil.upload(url, path);
        System.out.println(res);
    }

    @Test
    public void getQrCodeTicket() {
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="
                + accessTokenService.getAccessToken();
        String data = "{\"expire_seconds\":600,\"action_name\":\"QR_STR_SCENE\"," +
                "\"action_info\":{\"scene\":{\"scene_str\":\"situ\"}}}";
        String result = PureNetUtil.postJson(url, data);
        try {
            Map<String, String> map = mapper.readValue(result, Map.class);
            String ticket = map.get("ticket");
            System.out.println(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
