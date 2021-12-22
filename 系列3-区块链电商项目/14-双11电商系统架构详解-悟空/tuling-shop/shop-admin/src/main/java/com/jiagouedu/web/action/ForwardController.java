package com.jiagouedu.web.action;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by dylan on 15-1-18.
 */
@Controller
@RequestMapping("forward")
public class ForwardController {
    private String p = "/common/404";
    @RequestMapping
    public String forward(@RequestParam("p")String page){
        if(StringUtils.isBlank(page)){
            return p;
        }
        return page.trim();
    }
}
