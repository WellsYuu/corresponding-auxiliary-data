package com.tuling.springboot.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SampleController {

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "Hello World! zhangfei";
    }
}