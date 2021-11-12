package com.enjoy.james.argumentResolver;

import java.lang.reflect.Method;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enjoy.james.annotation.EnjoyService;

@EnjoyService("httpServletRequestArgumentResolver")
public class HttpServletRequestArgumentResolver implements ArgumentResolver {
	//判断传进来的参数是否为request
    public boolean support(Class<?> type, int paramIndex, Method method) {
        return ServletRequest.class.isAssignableFrom(type);
    }
    ////如果返回的参数是request,则直接返回
    public Object argumentResolver(HttpServletRequest request,
            HttpServletResponse response, Class<?> type, int paramIndex,
            Method method) {
        return request;
    }
    
}
