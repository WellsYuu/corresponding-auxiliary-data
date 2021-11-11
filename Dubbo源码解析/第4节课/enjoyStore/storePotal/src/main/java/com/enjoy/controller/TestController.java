package com.enjoy.controller;

import com.alibaba.dubbo.rpc.service.EchoService;
import com.alibaba.dubbo.rpc.service.GenericService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;


@Controller
public class TestController implements ApplicationContextAware{
    private ApplicationContext ctx;

    /**
     * 回声测试：扫一遍服务是否都已就绪
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public HashMap test(HttpServletRequest request, HttpServletResponse response) {
        String[] serviceIds = new String[]{"productService","userService","orderService","payService"};
        HashMap<String,String> retMap = new HashMap<>();

        Object ret = null;
        for (String id:serviceIds){
            try {
                EchoService echoService = (EchoService)ctx.getBean(id);
                ret = echoService.$echo("ok");
                retMap.put(id,ret.toString());
            } catch (Exception e) {
                retMap.put(id,"not ready");
            }
        }

        return retMap;

    }

    /**
     * 泛化调用
     * 当前项目，没有对应的接口---- com.enjoy.service.OtherService
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/other", method = RequestMethod.GET)
    @ResponseBody
    public String other(HttpServletRequest request, HttpServletResponse response) {
        GenericService genericService = (GenericService)ctx.getBean("otherService");

        Object ret = genericService.$invoke("getDetail",new String[]{"java.lang.String"},new Object[]{"name"});
        return ret.toString();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
}
