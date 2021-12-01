package com.gupao.vip.springwebmvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO:小马哥，写点注释吧！
 * 广告资源位...
 *
 * @author mercyblitz
 * @date 2017-10-11
 **/
@RestControllerAdvice(basePackages = "com.gupao.vip.springwebmvc.controller")
public class RestControllerAdvicer {

    @ExceptionHandler(value = {NullPointerException.class
            ,IllegalAccessException.class,
            IllegalStateException.class,
    })
    public Object handleNPE(
            Throwable throwable) {
        Map<String,Object> data = new HashMap<>();
        data.put("message",throwable.getMessage());
        return data;
    }

}
