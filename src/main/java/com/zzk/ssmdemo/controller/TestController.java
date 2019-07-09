package com.zzk.ssmdemo.controller;

import com.zzk.ssmdemo.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    @GetMapping("/getusers")
    @ResponseBody
    public List<User> getUsers() {
        List<User> list = new ArrayList<>();
        list.add(new User());

        return list;
    }

    @GetMapping("/test")
    public ModelAndView test(){
        return new ModelAndView("test");
    }
}
