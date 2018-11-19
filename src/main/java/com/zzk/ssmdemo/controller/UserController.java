package com.zzk.ssmdemo.controller;

import com.zzk.ssmdemo.entity.Result;
import com.zzk.ssmdemo.entity.User;
import com.zzk.ssmdemo.service.UserService;
import com.zzk.ssmdemo.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getuser/{id}")
    public Result<User> getUser(@PathVariable("id") Integer id){
        return ResultUtil.success(userService.getUserById(id));
    }

    @GetMapping("/insertuser/{username}")
    public Result<Integer> insertUser(@PathVariable("username") String username){
        User user = new User();
        user.setUsername(username);
        return ResultUtil.success(userService.insertUser(user));
    }
}
