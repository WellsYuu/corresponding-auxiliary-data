package com.tl.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tl.proxy.CglibProxy;
import com.tl.proxy.DynamicProxy;
import com.tl.proxy.StaticProxy;
import com.tl.service.DeptServiceImpl;
import com.tl.service.UsersServiceImpl;
import com.tl.service.impl.UsersService;

public class TestProxy {
	
	@Test
	public void testStaticProxy(){
		//生成对应的代理对象
		StaticProxy proxy = new StaticProxy(new UsersServiceImpl());
		proxy.add();
	}
	@Test
	public void testDynamicProxy(){
		//生成对应的代理对象
		DynamicProxy proxy = new DynamicProxy();
		//需要绑定对应的委托者
		UsersService service = (UsersService) proxy.bind(new DeptServiceImpl());
		//通过代理对象调用方法
		service.add();
		
		service.update();
	}
	
	@Test
	public void testCglibProxy(){
		//生成对应的代理对象
		CglibProxy proxy = new CglibProxy();
		//绑定对应的委托者
		DeptServiceImpl service = (DeptServiceImpl) proxy.bind(new DeptServiceImpl());
		//通过代理对象来调用方法
		service.add();
	}
	@Test
	public void testAop(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		UsersService service = (UsersService) context.getBean("usersService");
		service.add();
	}
}
