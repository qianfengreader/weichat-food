package com.qf.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.qf.common.BaseResp;
import com.qf.utils.CookieUtils;
import com.qf.utils.JwtUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 1. filterType()        // 拦截器何时进行拦截  // 请求执行前调用
 * 2. filterOrder()       // 拦截器的执行顺序==> 1-5个顺序,过滤器可以有多个
 * 3. shouldFilter()      // 返回false说明放过，不拦截，返回true，执行拦截
 * 4. run()               // 拦截后执行的方法
 *
 * 5. MyZuulFilter()      // 定义空参构造函数，开始的时候进行list赋值，创建放行请求的集合
 */
@Component
public class MyZuulFilter extends ZuulFilter {

    private static final List URL_LIST = new ArrayList<>();

    @Override
    public String filterType() {
        // 默认值
        return FilterConstants.PRE_TYPE;
    }


    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {

        // 获取请求上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        // 获取请求体/请求域
        HttpServletRequest request = currentContext.getRequest();
        // 获取请求响应结果
        HttpServletResponse response = currentContext.getResponse();
        // 获取请求路径
        String requestURI = request.getRequestURI();

        System.out.println(requestURI);

        // 判断是否放行
        CookieUtils cookieUtils = new CookieUtils();
        String token = cookieUtils.getToken(request);
        // 使用jwt解析token，进行解密
        JwtUtils jwtUtils = new JwtUtils();
        Map verify = jwtUtils.Verify(token);

        if (verify == null || verify.get("username") == null) {
            return true;
        }

        return true;
    }

    @Override
    public Object run() throws ZuulException {

        BaseResp baseResp = new BaseResp();
        baseResp.setCode(201);
        baseResp.setMessage("请求给Zuul网关拦截");
        return baseResp;
    }

    public MyZuulFilter() {

        URL_LIST.add("/weichat-user/user/login");
    }
}
