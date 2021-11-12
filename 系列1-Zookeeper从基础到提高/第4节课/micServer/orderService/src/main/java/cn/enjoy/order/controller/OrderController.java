package cn.enjoy.order.controller;

import cn.enjoy.order.pojo.Order;
import cn.enjoy.order.pojo.Product;
import cn.enjoy.order.utils.LoadBalance;
import cn.enjoy.order.utils.RamdomLoadBalance;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * Created by VULCAN on 2018/7/28.
 */
@RequestMapping("/order")
@RestController
public class OrderController {

    @Resource
    private RestTemplate restTemplate;

    private LoadBalance loadBalance = new RamdomLoadBalance();

    @RequestMapping("/getOrder/{id}")
    public Object getOrder(@PathVariable("id") String id ) {
        Product product = restTemplate.getForObject("http://"+loadBalance.choseServiceHost()+"/product/getProduct/1", Product.class);
        return new Order(id,"orderName",product);
    }
}
