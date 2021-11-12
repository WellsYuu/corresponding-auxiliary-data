package com.enjoy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.enjoy.service.OrderService;
import com.enjoy.service.ProductService;
import com.enjoy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RestController
public class IndexController {
    @Reference(check = true)
    private UserService userService;

    @Reference(check = true)
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public Map index(HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");
        String userView = userService.getDetail(id);
        String orderView = orderService.getDetail(id);
        String productView = productService.getDetail(id);

        Map map = new HashMap<>();
        map.put("userView", userView);
        map.put("orderView", orderView);
        map.put("productView", productView);
        return map;
    }

}
