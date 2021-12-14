package com.tl.controller;


import com.tl.annotiion.Controller;
import com.tl.annotiion.Qualifier;
import com.tl.annotiion.RequestMapping;
import com.tl.service.TlService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("/tl")
public class TlController {

    @Qualifier("tl")
    TlService tlService;

    @RequestMapping("/insert")
    public String insert(HttpServletRequest request, HttpServletResponse response){

        tlService.insert();
       return "ok";
    }

}
