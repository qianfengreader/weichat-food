package com.qf.controller;

import com.qf.common.BaseResp;
import com.qf.pojo.User;
import com.qf.pojo.resp.ReqUser;
import com.qf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public BaseResp login(@RequestBody ReqUser user) {

        return userService.login(user);
    }

    @RequestMapping("/sendEmail")
    public BaseResp sendEmail(@RequestParam("email")String email) {

        return userService.sendEmail(email);
    }

    @RequestMapping(value = "/registey",method = RequestMethod.POST)
    public BaseResp registey(@RequestBody User user) {

        return userService.registey(user);
    }
}