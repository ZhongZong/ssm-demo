package com.zzk.ssmdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author situliang
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public ModelAndView test(){
        return new ModelAndView("test");
    }
}
