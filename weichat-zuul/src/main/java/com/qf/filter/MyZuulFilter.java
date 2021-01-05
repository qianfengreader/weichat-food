package com.qf.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyZuulFilter extends ZuulFilter {

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

    @Override
    public Object run() throws ZuulException {
        return null;
    }
}
