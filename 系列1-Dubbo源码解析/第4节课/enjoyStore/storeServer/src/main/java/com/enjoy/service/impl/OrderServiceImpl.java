package com.enjoy.service.impl;

import com.enjoy.dao.OrderDao;
import com.enjoy.entity.OrderEntiry;
import com.enjoy.service.OrderService;
import com.enjoy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductService productService;


    @Override
    public OrderEntiry getDetail(String id) {
        OrderEntiry orderEntiry =  orderDao.getDetail(id);
        orderEntiry.addProduct(productService.getDetail("P001"));
        orderEntiry.addProduct(productService.getDetail("P002"));
        System.out.println(super.getClass().getName()+"被调用一次："+System.currentTimeMillis());
        return orderEntiry;
    }

    @Override
    public OrderEntiry submit(OrderEntiry order) {
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (1==order.getStatus()){
            System.out.println("警告：订单重复提交！");
            throw new RuntimeException("订单重复提交！");
        }
        System.out.println(super.getClass().getName()+"被调用一次："+System.currentTimeMillis());
        return orderDao.submit(order);
    }

    @Override
    public String cancel(OrderEntiry order) {
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(super.getClass().getName()+"被调用一次："+System.currentTimeMillis());
        return orderDao.cancel(order);
    }
}
