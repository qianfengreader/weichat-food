package com.qf.controller;

import com.qf.common.BaseResp;
import com.qf.pojo.User;
import com.qf.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public BaseResp login(@RequestBody Map map){
        return userService.findByUsername(map);
    }
    @RequiresPermissions(value = {"findAll"})
    @RequestMapping("/findAll")
    public BaseResp findAll(){
        return userService.findAll();
    }
    @RequestMapping("/registry")
    public BaseResp registry(@RequestBody User user){
        return userService.registry(user);
    }
    @RequestMapping("/editStatus/{id}")
    public BaseResp editStatus(@PathVariable("id")Integer id){
        return userService.editStatus(id);
    }
    @RequestMapping("/updateById")
    public BaseResp updateById(@RequestBody User user){
        return userService.updateById(user);
    }
    @RequestMapping("/deleteById")
    public BaseResp deleteById(@RequestBody Map map){
        return userService.deleteById(map);
    }
}
