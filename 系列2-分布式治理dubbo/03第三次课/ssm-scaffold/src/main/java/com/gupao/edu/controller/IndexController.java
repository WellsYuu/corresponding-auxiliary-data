package com.gupao.edu.controller;

import com.gupao.edu.controller.support.ResponseData;
import com.gupao.edu.controller.support.ResponseEnum;
import com.gupao.vip.mic.dubbo.order.DoOrderRequest;
import com.gupao.vip.mic.dubbo.order.DoOrderResponse;
import com.gupao.vip.mic.dubbo.order.IOrderServices;
import com.gupao.vip.mic.dubbo.user.IUserService;
import com.gupao.vip.mic.dubbo.user.bean.UserLoginRequest;
import com.gupao.vip.mic.dubbo.user.bean.UserLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 */
@Controller
@RequestMapping("/index/")
public class IndexController extends BaseController{

    @Autowired
    IOrderServices orderServices;

    @Autowired
    IUserService userService;


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
        UserLoginRequest request1= new UserLoginRequest();
        request1.setName(loginname);
        request1.setPassword(password);
        UserLoginResponse response=userService.login(request1);
        if("000000".equals(response.getCode())){
            request.getSession().setAttribute("user","user");
            return setEnumResult(ResponseEnum.SUCCESS, "/");
        }
        ResponseData data=new ResponseData();
        data.setMessage(response.getMemo());
        data.setStatus(ResponseEnum.FAILED.getCode());
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
}
