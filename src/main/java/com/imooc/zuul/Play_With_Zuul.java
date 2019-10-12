package com.imooc.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.filters.ZuulServletFilter;
import com.netflix.zuul.http.ZuulServlet;
import com.netflix.zuul.ZuulRunner;

import javax.servlet.http.HttpServletRequest;

public class Play_With_Zuul extends ZuulFilter {

    @Override
    public String filterType() {
        return null;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        System.out.print(String.format("send %s request to %s",request.getMethod(),request.getRequestURL()));
        return null;
    }
}
