package com.zzk.ssmdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzk.ssmdemo.service.AccessTokenService;
import com.zzk.ssmdemo.service.MenuService;
import com.zzk.ssmdemo.service.TemplateService;
import com.zzk.ssmdemo.utils.PureNetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

/**
 * @author situliang
 */
@RestController
public class TestController {

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private TemplateService templateService;

    private ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/test")
    public ModelAndView test() {
        return new ModelAndView("test");
    }

    @GetMapping("/accesstoken")
    public String getAccessToken() {
        return accessTokenService.getAccessToken();
    }

    @GetMapping("/addmenu")
    public void addMenu() {
        menuService.addMenu();
    }

    @GetMapping("/sendtemplatemessage")
    public void sendTemplateMessage(){
        templateService.sendTemplateMessage();
    }

    @GetMapping("/getticket")
    public String getTicket(){
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="
                + accessTokenService.getAccessToken();
        String data = "{\"expire_seconds\":600,\"action_name\":\"QR_STR_SCENE\"," +
                "\"action_info\":{\"scene\":{\"scene_str\":\"situ\"}}}";
        String result = PureNetUtil.postJson(url, data);
        try {
            Map<String, String> map = mapper.readValue(result, Map.class);
            String ticket = map.get("ticket");
            System.out.println(map);
            return ticket;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
