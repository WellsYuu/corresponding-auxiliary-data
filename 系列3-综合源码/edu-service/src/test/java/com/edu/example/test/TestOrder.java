package com.edu.example.test;


import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.edu.example.bean.Order;
import com.edu.example.service.OrderService;

public class TestOrder extends TestSupport{
	private static Logger logger = LogManager.getLogger(TestOrder.class);
	@Resource
	private OrderService orderService;
	
	@Test
	public void test01(){
		Order order = new Order();
		order.setOrderNo("zhangfei001");
		order.setTitle("张飞");
		order.setAmount(1.1f);
		order.setStatus(1);
		orderService.insertOrder(order);
	}
	public static void main(String[] args) {
		Order order = new Order();
		order.setOrderNo("zhangfei001");
		order.setTitle("张飞");
		order.setAmount(1.1f);
		order.setStatus(1);
		String json = JSON.toJSONString(order);
		System.out.println(json);
	}
}
