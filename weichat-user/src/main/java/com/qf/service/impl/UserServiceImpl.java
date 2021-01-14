package com.qf.service.impl;
import com.qf.utils.JwtUtils;
import com.qf.common.BaseResp;
import com.qf.dao.UserMapper;
import com.qf.pojo.User;
import com.qf.service.UserService;
import com.qf.utils.RedisUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
   @Autowired
   RedisUtils redisUtils;
   @Autowired
   UserMapper userMapper;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public BaseResp findByUsername(Map map) {
        BaseResp baseResp = new BaseResp();
        User byUsername = userMapper.findByUsername(map.get("username").toString());
        if (byUsername!=null){
            if ((map.get("password").toString()).equals(byUsername.getPassword())){
                UUID uuid=UUID.randomUUID();
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(map.get("username").toString(),map.get("password").toString());
                subject.login(token);
                redisUtils.set(uuid.toString(),byUsername);
                JwtUtils jwtUtils = new JwtUtils();
                //放置在 jwt中的负载部分
                Map maps = new HashMap<>();
                maps.put("username",byUsername.getUsername());
                maps.put("id",byUsername.getId());
                String tokens = jwtUtils.token(maps);
                baseResp.setCode(200);
                baseResp.setData(tokens);
                baseResp.setMessage("登陆成功");
                return baseResp;
            }else{
                baseResp.setCode(202);
                baseResp.setMessage("密码有误");
                return baseResp;
            }
        }else{
            baseResp.setCode(201);
            baseResp.setMessage("用户不存在");
            return baseResp;
        }
    }
    @Override
    public BaseResp findAll() {
        BaseResp baseResp = new BaseResp();
        List<User> user = userMapper.findAll();
        if (user.size()>0){
            baseResp.setData(user);
            baseResp.setCode(200);
            baseResp.setMessage("查询成功");
            return baseResp;
        }
        baseResp.setCode(201);
        baseResp.setMessage("查询失败");
        return baseResp;
    }
    @Override
    public BaseResp registry(User user) {
        //1.将用户信息存储到数据库中，将用户的id以及email地址获取，发送给rabbitmq
        BaseResp baseResp = new BaseResp();
        if (user.getEmail() == null) {
            baseResp.setMessage("邮箱不能为空 ");
            baseResp.setCode(201);
            return baseResp;
        }
        User byUserName = userMapper.findByUsername(user.getUsername());
        if (byUserName != null) {
            try {
                baseResp.setMessage("用户名重复");
            } catch (Exception e) {
                e.printStackTrace();
            }
            baseResp.setCode(202);
            return baseResp;
        }
        User byEmail = userMapper.findByEmail(user.getEmail());
        if (byEmail != null) {
            baseResp.setMessage("邮箱已被注册！忘记密码请重置");
            baseResp.setCode(203);
            return baseResp;
        }
        //对象的复制
        User tbUser = new User();
        BeanUtils.copyProperties(user, tbUser);
        tbUser.setStatus(0);
        Integer i  = userMapper.registry(tbUser);
        //获取到存储后的用户的id,以及邮箱地址
        User users = userMapper.findByUsername(user.getUsername());
        Map map = new HashMap<>();
        map.put("id", users.getId());
        map.put("email", users.getEmail());
        rabbitTemplate.convertAndSend("", "send-mail", map);
        //返回
        baseResp.setCode(200);
        baseResp.setMessage("注册成功！请注意查收邮件，激活账号");
        return baseResp;
    }
    @Override
    public BaseResp editStatus(Integer id) {
        BaseResp baseResp = new BaseResp();
        //通过id查询
        User  byId = userMapper.findById(id);
        if (byId==null){
            baseResp.setMessage("没有该用户");
            return baseResp;
        }
        if (byId.getStatus()!=null&&byId.getStatus()==1){
            baseResp.setMessage("用户以激活，不能重复操作");
            return baseResp;
        }
        //设置status为1
        byId.setStatus(1);
        userMapper.updateUserById(byId);
        baseResp.setMessage("激活成功");
        baseResp.setCode(200);
        return baseResp;
    }
    @Override
    public BaseResp updateById(User user) {
        BaseResp baseResp = new BaseResp();
        User byName = userMapper.findByUsername(user.getUsername());
        if (byName.getUsername().equals(user.getUsername()) && byName.getId()!=user.getId()){
            baseResp.setCode(202);
            baseResp.setMessage("用户名已存在");
            return  baseResp;
        }
        Integer status = userMapper.updateById(user);
        if (status>0){
            baseResp.setCode(200);
            baseResp.setMessage("修改成功");
            return baseResp;
        }
        baseResp.setCode(201);
        baseResp.setMessage("修改失败");
        return baseResp;
    }
    @Override
    public BaseResp deleteById(Map map) {
        BaseResp baseResp = new BaseResp();
        Integer i = userMapper.deleteById((Integer)map.get("id"));
        if (i>0){
            baseResp.setCode(200);
            baseResp.setMessage("删除成功");
            return baseResp;
        }
        baseResp.setCode(201);
        baseResp.setMessage("删除失败");
        return baseResp;
    }
}
