package com.enjoy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.service.EchoService;
import com.enjoy.service.OrderService;
import com.enjoy.service.UserService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class IndexController implements ApplicationContextAware{
   private ApplicationContext context;

    @Reference
    private UserService userService;

    @Reference
    private OrderService orderService;


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");
        String userView = userService.getDetail(id);
        String orderView = orderService.getDetail(id);

        request.setAttribute("userView", userView);
        request.setAttribute("orderView", orderView);
        return "index";
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ResponseBody
    public String check(HttpServletRequest request, HttpServletResponse response) {
        String serviceId = request.getParameter("serviceId");
        Object checkMsg = serviceId + " is not ready";
        try {
            EchoService echoService = (EchoService) context.getBean(serviceId);
            if ("OK".equals(echoService.$echo("OK"))){
                checkMsg = serviceId + " is OK";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(checkMsg);;
        return checkMsg.toString();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
