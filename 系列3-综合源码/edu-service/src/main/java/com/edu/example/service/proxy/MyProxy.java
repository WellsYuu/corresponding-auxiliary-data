/**
 * 
 */
package com.edu.example.service.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理的处理类
 * @author 张飞老师
 */
public class MyProxy implements InvocationHandler{
	private Object target;// 目标对象
	
	public MyProxy(Object target){
		this.target = target;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		if(method.getName().startsWith("test")){
			System.out.println("===========加入了额外的处理=========");
		}
		
		return method.invoke(target, args);// 调用目标对象的方法
	}
	
	/**
	 * proxy.fun() 是可以被代理的
	 * object.fun() 是不能被代理的
	 * @param args
	 */
	public static void main(String[] args) {
		// 1.创建真实对象
		// 2.真实对象交给代理
		MyProxy hanlder = new MyProxy(new TestImpl());
		
		// 3获取代理对象
		Test proxy = (Test)Proxy.newProxyInstance(MyProxy.class.getClassLoader(), 
				new Class[]{Test.class}, hanlder);
		// 4.代理调用目标方法
		proxy.test();
//		proxy.test1();
//		proxy.abc();
	}

}
