package com.enjoy.james.handlerAdapter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enjoy.james.annotation.EnjoyService;
import com.enjoy.james.argumentResolver.ArgumentResolver;

@EnjoyService("jamesHandlerAdapter")
public class JamesHandlerAdapter implements HandlerAdapterService {
    //对method方法里的参数进行处理
    public Object[] hand(HttpServletRequest request,//需要传入request,拿请求的参数
            HttpServletResponse response, Method method,//执行的方法,可以拿到当前待执行的方法有哪些参数
            Map<String, Object> beans) {
        //拿到当前待执行的方法有哪些参数
        Class<?>[] paramClazzs = method.getParameterTypes();
        //根据参数的个数,new 一个参数的数组,将方法里的所有参数赋值到args来
        Object[] args = new Object[paramClazzs.length];
        
        //1、要拿到所有实现了ArgumentResolver这个接口的实现类
        Map<String, Object> argumentResolvers = getBeansOfType(beans,
                ArgumentResolver.class);
        
        int paramIndex = 0;
        int i = 0;
        //对每一个参数进行循环,每个参数都有特殊处理(比如RequestParam的处理类为 RequestParamArgumentResolver )
        for (Class<?> paramClazz : paramClazzs) {
        	//哪个参数对应了哪个参数解析类,用策略模式来找
            for (Map.Entry<String, Object> entry : argumentResolvers.entrySet()) {
                ArgumentResolver ar = (ArgumentResolver)entry.getValue();
                
                if (ar.support(paramClazz, paramIndex, method)) {
                    args[i++] = ar.argumentResolver(request,
                            response,
                            paramClazz,
                            paramIndex,
                            method);
                }
            }
            paramIndex++;
        }
        
        return args;
    }
    //获取实现了ArgumentResolver接口的所有实例(其实就是每个参数的注解实例)
    private Map<String, Object> getBeansOfType(Map<String, Object> beans,//所有bean
            Class<?> intfType) //类型的实例
    {
        
        Map<String, Object> resultBeans = new HashMap<String, Object>();
        
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
        	//拿到实例-->反射对象-->它的接口(接口有多实现,所以为数组)
            Class<?>[] intfs = entry.getValue().getClass().getInterfaces();
            
            if (intfs != null && intfs.length > 0) {
                for (Class<?> intf : intfs) {
                	//接口的类型与传入进来的类型一样,把实例加到resultBeans里来
                	if (intf.isAssignableFrom(intfType)) {
                        resultBeans.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }
        
        return resultBeans;
    }
    
}
