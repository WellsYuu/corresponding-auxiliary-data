package com.enjoy.controller;

import java.util.UUID;
import java.util.concurrent.Callable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
 


@Controller
public class AsyncOrderController {
	
	//其实相当于我们说的tomcat的线程1，来处理用户请求，并将请求的操作放到Queue队列里
	@ResponseBody
	@RequestMapping("/createOrder")
	public DeferredResult<Object> createOrder(){
		DeferredResult<Object> deferredResult = new DeferredResult<>((long)10000, "create fail...");
			
		JamesDeferredQueue.save(deferredResult);
		
		return deferredResult;
	}
	
	////其实相当于我们说的tomcat的线程N，来处理用户请求，并将请求的操作放到Queue队列里
	@ResponseBody
	@RequestMapping("/create")
	public String create(){
		//创建订单（按真实操作应该是从订单服务取，这里直接返回）
		String order = UUID.randomUUID().toString();//模拟从订单服务获取的订单信息（免调接口）
		DeferredResult<Object> deferredResult = JamesDeferredQueue.get();
		deferredResult.setResult(order);
		return "create success, orderId == "+order;
	}
	
	
	
	
	
	@ResponseBody
	@RequestMapping("/order01")
	public Callable<String> order01(){
		System.out.println("主线程开始..."+Thread.currentThread()+"==>"+System.currentTimeMillis());
		
		Callable<String> callable = new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println("副线程开始..."+Thread.currentThread()+"==>"+System.currentTimeMillis());
				Thread.sleep(2000);
				System.out.println("副线程开始..."+Thread.currentThread()+"==>"+System.currentTimeMillis());
				return "order buy successful........";
			}
		};
		
		System.out.println("主线程结束..."+Thread.currentThread()+"==>"+System.currentTimeMillis());
		return callable;
	}

}
