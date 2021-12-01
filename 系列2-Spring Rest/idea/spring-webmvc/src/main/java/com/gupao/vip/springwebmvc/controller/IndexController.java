package com.gupao.vip.springwebmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODO:小马哥，写点注释吧！
 * 广告资源位...
 *
 * @author mercyblitz
 * @date 2017-10-11
 **/
@Controller
public class IndexController {

    @RequestMapping("")
    public String index(){
        return "index";
    }
}
