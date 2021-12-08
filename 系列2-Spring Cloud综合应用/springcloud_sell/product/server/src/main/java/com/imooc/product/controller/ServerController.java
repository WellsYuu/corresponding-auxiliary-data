package com.imooc.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 廖师兄
 * 2017-12-10 20:37
 */
@RestController
public class ServerController {

    @GetMapping("/msg")
    public String msg() {
        return "this is product' msg";
    }
}
