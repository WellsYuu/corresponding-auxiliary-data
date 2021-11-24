package com.gupao.edu.controller;

import com.gupao.edu.controller.support.ResponseData;
import com.gupao.vip.mic.dubbo.user.IUserCoreService;
import com.gupao.vip.mic.dubbo.user.IUserLoginService;
import com.gupao.vip.mic.dubbo.user.IUserQueryService;
import com.gupao.vip.mic.dubbo.user.dto.UserLoginRequest;
import com.gupao.vip.mic.dubbo.user.dto.UserLoginResponse;
import com.gupao.vip.mic.dubbo.user.dto.UserQueryRequest;
import com.gupao.vip.mic.dubbo.user.dto.UserQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 */
@Controller
@RequestMapping("/index/")
public class IndexController extends BaseController{
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    @Autowired
    IUserCoreService userCoreServices;

    @Autowired
    IUserQueryService userQueryService;


    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(HttpServletRequest request){
        if(request.getSession().getAttribute("user")==null){
            return "/login";
        }
        return "/index";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "/login";
    }

    @RequestMapping(value="/submitLogin",method=RequestMethod.POST)
    @ResponseBody
    public ResponseData submitLogin(HttpServletRequest request,String loginname,String password){
        UserLoginRequest loginRequest=new UserLoginRequest();
        loginRequest.setUsername(loginname);
        loginRequest.setPassword(password);
        UserLoginResponse loginResponse=userCoreServices.login(loginRequest);
        ResponseData data=new ResponseData();
        data.setMessage(loginResponse.getMsg());
        data.setCode(loginResponse.getCode());
        data.setData("/");
        if("000000".equals(loginResponse.getCode())){
            request.getSession().setAttribute("user","user");
        }
        return data;
    }

    /**
     * 退出
     * @return
     */
    @RequestMapping(value="/logout",method =RequestMethod.GET)
    public String logout(HttpServletRequest request){
        try {
            request.getSession().removeAttribute("user");
        } catch (Exception e) {
            LOG.error("errorMessage:" + e.getMessage());
        }
        return redirectTo("/index/login.shtml");
    }

    @RequestMapping(value="/callWithLimiter",method =RequestMethod.GET)
    public @ResponseBody ResponseData callWithLimiter() throws InterruptedException {
        ResponseData data=new ResponseData();
        for(int i=0;i<10;i++){
            fixedThreadPool.submit(()->{
                UserQueryRequest request=new UserQueryRequest();
                request.setUid(1);
                UserQueryResponse response=userQueryService.getUserByIdWithLimiter(request);
                LOG.info("response:"+response);
                if(response.getCode().equals("001008")){
                    data.setCode(response.getCode());
                    data.setMessage(response.getMsg());
                    data.setData(response);
                }
            });
        }
        fixedThreadPool.shutdown();
        fixedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        return data;
    }

    @RequestMapping(value="/callWithNormal",method =RequestMethod.GET)
    public @ResponseBody ResponseData callWithNormal(){
        UserQueryRequest request=new UserQueryRequest();
        request.setUid(1);
        UserQueryResponse response=userQueryService.getUserById(request);
        LOG.info("response:"+response);
        ResponseData data=new ResponseData();
        data.setCode(response.getCode());
        data.setMessage(response.getMsg());
        data.setData(response);
        return data;
    }
}
