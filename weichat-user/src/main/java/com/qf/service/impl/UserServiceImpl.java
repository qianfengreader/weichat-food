package com.qf.service.impl;

import com.qf.common.BaseResp;
import com.qf.dao.UserMapper;
import com.qf.pojo.User;
import com.qf.pojo.resp.ReqUser;
import com.qf.service.UserService;
import com.qf.utils.JwtUtils;
import com.qf.utils.ReidsUtils;
import io.lettuce.core.RedisURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    ReidsUtils reidsUtils;

    @Autowired
    JwtUtils jwtUtils;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public BaseResp login(ReqUser user) {

        BaseResp baseResp = new BaseResp();
        if (StringUtils.isEmpty(user.getPassword()) || user.getPassword() == null || StringUtils.isEmpty(user.getUsername()) || user.getUsername() == null) {
            baseResp.setMessage("请输入正确的用户名或者密码");
            baseResp.setCode(201);
            return baseResp;
        }

        User byUsername = userMapper.findByUsername(user.getUsername());
        if (byUsername == null) {
            baseResp.setMessage("用户名不存在");
            baseResp.setCode(201);
            return baseResp;
        }

        // 判断密码是否正确
        if (user.getPassword().equals(byUsername.getPassword())) {

            // 以前放在redis中或者session中，现在使用JWT进行登录认证
            // 自定义加密算法
            Map map = new HashMap<>();
            map.put("username",byUsername.getUsername());
            map.put("id",byUsername.getId());
            // 生成加密后的token
            String token = jwtUtils.token(map);
            baseResp.setCode(200);
            baseResp.setMessage("登录成功");
            baseResp.setData(token);
            return baseResp;
        }
        baseResp.setMessage("密码输入错误");
        baseResp.setCode(201);
        return baseResp;
     }

    @Override
    public BaseResp sendEmail(String email) {

        BaseResp baseResp = new BaseResp();
        if (email == null || StringUtils.isEmpty(email)) {
            baseResp.setMessage("请输入邮箱地址");
            baseResp.setCode(201);
            return baseResp;
        }

        // 生成验证码code
        Random random = new Random();
        StringBuffer code = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            int num = random.nextInt(10);
            code.append(num);
        }

        // 发送邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(email);
        // 主题
        simpleMailMessage.setSubject("WeiChat点餐注册码，2分钟内有效");
        // 主体
        simpleMailMessage.setText(code.toString());
        javaMailSender.send(simpleMailMessage);
        //发送后 存入 redis
        reidsUtils.set(email,code.toString());
        //设置过期时间 5分钟
        reidsUtils.expire(email,120);

        baseResp.setCode(200);
        baseResp.setMessage("发送成功");
        return baseResp;
    }

    @Override
    public BaseResp registey(User user) {

        BaseResp baseResp = new BaseResp();
        if (user == null) {
            baseResp.setCode(200);
            baseResp.setMessage("用户为空，注册失败");
            return baseResp;
        }

        // 是否注册过
        String username = user.getUsername();
        User byUsername = userMapper.findByUsername(username);
        if (byUsername != null) {
            baseResp.setCode(200);
            baseResp.setMessage("用户名已被注册");
            return baseResp;
        }

        // 判断验证码是否正确
        String code = user.getCode();
        String email = user.getEmail();
        if (code == null || StringUtils.isEmpty(code)) {
            baseResp.setCode(201);
            baseResp.setMessage("请输入验证码");
            return baseResp;
        }
        String o = (String) reidsUtils.get(email);
        if (o != null && code.equals(o)) {
            userMapper.saveAndFlush(user);
            baseResp.setMessage("注册成功");
            baseResp.setCode(200);
            return baseResp;
        }

        baseResp.setCode(202);
        baseResp.setMessage("注册失败，无法查找原因");
        return baseResp;
    }
}