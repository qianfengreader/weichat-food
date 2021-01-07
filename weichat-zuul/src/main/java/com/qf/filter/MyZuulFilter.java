package com.qf.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class MyZuulFilter extends ZuulFilter {

    private static final List URL_LIST = new ArrayList<>();

    //拦截器何时进行拦截  // 请求执行前调用
    @Override
    public String filterType() {
        // 默认值
        return FilterConstants.PRE_TYPE;
    }

    //拦截器的执行顺序==> 1-5个顺序,过滤器可以有多个
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }

    // 返回false说明放过，不拦截，返回true，执行拦截
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

        return false;
    }

    // 拦截后执行的方法
    @Override
    public Object run() throws ZuulException {
        return null;
    }

    // 定义空参构造函数，开始的时候进行list赋值，创建放行请求的集合
    public MyZuulFilter() {


    }
}
