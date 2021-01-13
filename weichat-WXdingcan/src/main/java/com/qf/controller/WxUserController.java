package com.qf.controller;

import com.qf.common.ResultVO;
import com.qf.dao.UserRepository;
import com.qf.meiju.ResultEnum;
import com.qf.pojo.UserForm;
import com.qf.utils.ResultVOUtil;
import com.qf.yichang.DianCanException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Slf4j
public class WxUserController {

    @Autowired
    UserRepository repository;

    //创建订单
    @PostMapping("/save")
    public ResultVO create(@Valid UserForm userForm,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("参数不正确, userForm={}", userForm);
            throw new DianCanException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        UserForm byOpenid = repository.findByOpenid(userForm.getOpenid());
        if (byOpenid == null){  //第一次注册
            return ResultVOUtil.success(repository.saveAndFlush(userForm));
        }
        byOpenid.setTel(userForm.getTel());
        byOpenid.setUsername(userForm.getUsername());
        return ResultVOUtil.success(repository.save(byOpenid));
    }

    @GetMapping("/getUserInfo")
    public ResultVO getUserInfo(@RequestParam("openid") String openid) {
        UserForm byOpenid = repository.findByOpenid(openid);
        return ResultVOUtil.success(byOpenid);
    }

}
