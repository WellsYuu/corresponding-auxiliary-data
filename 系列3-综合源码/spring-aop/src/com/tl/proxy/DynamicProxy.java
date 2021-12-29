package com.tl.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK的动态代理 一个接口：InvocationHandler 一个类：Proxy
 * 
 * @author Administrator
 * 
 */
public class DynamicProxy implements InvocationHandler {
	// 只写一个代理类，都能代理
	private Object delegationObj;// 委托类

	/**
	 * 返回代理对象
	 * 
	 * @param delegationObj
	 * @return
	 */
	public Object bind(Object delegationObj) {
		this.delegationObj = delegationObj;
		return Proxy.newProxyInstance(
				delegationObj.getClass().getClassLoader(), delegationObj
						.getClass().getInterfaces(), this);
	}

	@Override
	/**
	 * 代理对象调用对应的方法时，会自动调用invoke方法
	 * 有统一的入口了，设计模式中的门面模式
	 */
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		// 写对应的增强代码
		System.out.println("日志记录开始");
		if(method.getName().indexOf("add")>=0){
			System.out.println("添加用户");
		}
		// 真实的服务，必须由委托者来提供
		Object obj = method.invoke(delegationObj, args);
		System.out.println("日志记录结束");
		return obj;
	}
}
