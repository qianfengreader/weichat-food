package com.qf.gloableExepiton;

import com.qf.common.BaseResp;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class ExcepitonController {
    @ExceptionHandler(AuthorizationException.class)
    public BaseResp AuthonewrizationEx(){
        BaseResp baseResp = new BaseResp();
        baseResp.setCode(300);
        baseResp.setMessage("无权限访问");
        return baseResp;
    }
    @ExceptionHandler(UnauthenticatedException.class)
    public BaseResp unauthenticatedException(){
        BaseResp baseResp = new BaseResp();
        baseResp.setCode(400);
        baseResp.setMessage("未登录");
        return baseResp;
    }
}
