package cn.enjoy.product.controller;

import cn.enjoy.product.pojo.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by VULCAN on 2018/7/28.
 */

@RestController
@RequestMapping("/product")
public class ProductController {

    @RequestMapping("/getProduct/{id}")
    public Object getProduct(HttpServletRequest request, @PathVariable("id") String id) {
        return new Product(id,"name:"+request.getLocalPort());
    }
}