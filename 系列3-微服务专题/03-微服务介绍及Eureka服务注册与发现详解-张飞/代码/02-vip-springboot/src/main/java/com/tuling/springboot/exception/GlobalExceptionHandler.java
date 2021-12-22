package com.tuling.springboot.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) 
    		throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("msg", "异常咯...");
        mav.setViewName("error");
        return mav;
    }
}