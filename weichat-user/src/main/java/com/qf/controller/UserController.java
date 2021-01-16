package com.qf.controller;

import com.qf.common.BaseResp;
import com.qf.pojo.User;
import com.qf.service.UserService;
import com.qf.utils.RedisUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RedisUtils redisUtils;

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


    // 前后端 公用的注销登录
    @RequestMapping(value = "/exit", method = RequestMethod.POST)
    public BaseResp exit(HttpServletRequest request, HttpServletResponse response) {

        BaseResp baseResp = new BaseResp();

        Cookie[] cookies = request.getCookies();
        String token = "";
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {

                // 删除redis
                token = cookie.getValue();
                redisUtils.del(token);

                baseResp.setCode(200);
                baseResp.setMessage("已退出当前登录");
            }
            if ("JSESSIONID".equals(cookie.getName())) {

                // 删除redis
                token = cookie.getValue();
                redisUtils.del(token);

                baseResp.setCode(201);
                baseResp.setMessage("已退出当前登录");
            }
        }
        return baseResp;
    }
    //从token中获取用户信息
    @RequestMapping(value = "/findUserFromToken",method = RequestMethod.POST)
    public BaseResp findUserFromToken(HttpServletRequest request){
        BaseResp userByToken = userService.findUserByToken(request);

        return userByToken;
    }




}
