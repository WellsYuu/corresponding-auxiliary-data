package com.jiagouedu.web.action;

import com.jiagouedu.web.util.LoginUserHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后端首页
 */
@Controller
@RequestMapping("/")
public class IndexAction  {
    private static final String page_home = "/manage/system/home";
    @RequestMapping({"/","/index"})
    public String index() {
        if(LoginUserHolder.getLoginUser() == null){
            return "redirect:/manage/user/login";
        }
        return page_home;
    }

}
