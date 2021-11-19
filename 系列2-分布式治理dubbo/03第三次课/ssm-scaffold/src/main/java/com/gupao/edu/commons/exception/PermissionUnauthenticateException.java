package com.gupao.edu.commons.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author qingyin
 * @date 2016/8/26
 */
public class PermissionUnauthenticateException implements HandlerExceptionResolver {

    Logger LOG = LoggerFactory.getLogger(PermissionUnauthenticateException.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        ModelAndView mv = new ModelAndView();
        try {
            if(isAjax(request)){
                response.setStatus(HttpStatus.OK.value()); //设置状态码
                response.setContentType(MediaType.APPLICATION_JSON_VALUE); //设置ContentType
                response.setCharacterEncoding("UTF-8"); //避免乱码
                response.setHeader("Cache-Control", "no-cache, must-revalidate");
                response.getWriter().write("{\"status\":300,\"message\":\"服务器处理异常,请联系管理员处理\"}");
            }else{
                mv.setViewName("/common/error/500");
            }
        } catch (IOException e1) {
            LOG.error("异常捕捉错误:"+e1);
        }catch (Exception e2){
            LOG.error("系统异常："+e2);
        }
        return mv;
    }
    public static boolean isAjax(ServletRequest request){
        return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
    }
}
