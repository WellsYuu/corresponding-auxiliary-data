package com.enjoy.Listenercontroller;

import java.io.Console;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

@Component
public class UserService {

	public void login(String name, long waitSec, DeferredResult<String> deferredResult) throws InterruptedException {
		System.out.println("login方法里的线程开始..." + Thread.currentThread() + "==>" + System.currentTimeMillis());

		Thread.currentThread().sleep(waitSec);
		if ("james".equals(name)) {
			deferredResult.setResult("login Successful--name=" + name); // 此时就通知MVC异步处理已经完成，可以生成HTTP响应。因此后面的代码不会造成HTTP响应的延迟
		} else {
			deferredResult.setResult("login failed--name=" + name);
		}
		System.out.println("login方法里的线程结束..." + Thread.currentThread() + "==>" + System.currentTimeMillis());
	}

}