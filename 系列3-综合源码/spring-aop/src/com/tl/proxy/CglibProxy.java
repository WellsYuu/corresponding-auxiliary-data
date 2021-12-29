package com.tl.proxy;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor {
	

	private Object delegtionObj;//委托对象
	//缺少了代理对象和委托者绑定
	public Object bind(Object delegtionObj){
		this.delegtionObj = delegtionObj;
		//如何创建对应的代理对象
		//Cglib中的核心对象
		Enhancer enhancer = new Enhancer();
		//指定委托类为父类
		enhancer.setSuperclass(delegtionObj.getClass());
		//使用代理，需要一个对应的代理对象
		enhancer.setCallback(this);
		//产生对应的子类字节码
		Object result = enhancer.create();
		return result;
	}
	
	@Override
	/**
	 * 代理类对象调用对应的方法，自动调用intercept
	 */
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		System.out.println("Cglib代理里日志记录：");
		//委托类变为了父类 调用真正的服务提供者
		Object result = proxy.invokeSuper(obj, args);
		System.out.println("Cglib代理里日志记录结束");
		return result;
	}

}
