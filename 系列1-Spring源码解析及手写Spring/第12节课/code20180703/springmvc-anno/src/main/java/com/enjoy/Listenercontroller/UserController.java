package com.enjoy.Listenercontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	//用户登录
	@RequestMapping(value = "/login")
	@ResponseBody
	public DeferredResult<String> login(String name) {
		System.out.println("主线程开始..." + Thread.currentThread() + "==>" + System.currentTimeMillis());
		// 超时时间为10s，超时返回"failed"。
		DeferredResult<String> deferredResult = new DeferredResult<String>(10000L, "----------failed----------");

		new Thread(new Runnable(){
			@Override
			public void run() {
				System.out.println("副线程开始..." + Thread.currentThread() + "==>" + System.currentTimeMillis());
				try {
					userService.login(name, 3000, deferredResult);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("副线程开始..." + Thread.currentThread() + "==>" + System.currentTimeMillis());
			}}).start();
		

		System.out.println("主线程结束..." + Thread.currentThread() + "==>" + System.currentTimeMillis());
		return deferredResult;
	}

}