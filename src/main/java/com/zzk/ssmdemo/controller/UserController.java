package com.zzk.ssmdemo.controller;

import com.zzk.ssmdemo.entity.Result;
import com.zzk.ssmdemo.entity.User;
import com.zzk.ssmdemo.enums.ResultEnum;
import com.zzk.ssmdemo.exception.UserException;
import com.zzk.ssmdemo.service.UserService;
import com.zzk.ssmdemo.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getuser/{id}")
    public Result<User> getUser(@PathVariable("id") Integer id) {
        return ResultUtil.success(userService.getUserById(id));
    }

    @PostMapping("/insertuser/{username}")
    public Result<Integer> insertUser(@PathVariable("username") String username) {
        User user = new User();
        user.setUsername(username);
        userService.insertUser(user);
        return ResultUtil.success();
    }

    @DeleteMapping("/deleteuser/{id}")
    public Result<Integer> deleteUser(@PathVariable("id") Integer id) {
        if (id == null || id < 1) {
            throw new UserException(ResultEnum.PARAMETER_ERROR);
        } else {
            userService.deleteUser(id);
            return ResultUtil.success();
        }
    }

    @PutMapping("/updateuser")
    public Result<Integer> updateUser(User user) {
        if (user == null || user.getUserId() == null || user.getUserId() < 1) {
            throw new UserException(ResultEnum.PARAMETER_ERROR);
        } else {
            userService.updateUser(user);
            return ResultUtil.success();
        }
    }
}
