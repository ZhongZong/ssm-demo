package com.zzk.ssmdemo.controller;

import com.zzk.ssmdemo.service.AccessTokenService;
import com.zzk.ssmdemo.service.MenuService;
import com.zzk.ssmdemo.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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

}
