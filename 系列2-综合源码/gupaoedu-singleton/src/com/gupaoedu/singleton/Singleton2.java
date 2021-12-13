package com.gupaoedu.singleton;

//懒汉式单例.保证线程安全
public class Singleton2 {
	//1、第一步先将构造方法私有化
	private Singleton2() {}
	//2、然后声明一个静态变量保存单例的引用
	private static Singleton2 single=null;
	//3、通过提供一个静态方法来获得单例的引用
	//为了保证多线程环境下正确访问，给方法加上同步锁synchronized
	//慎用  synchronized 关键字，阻塞，性能非常低下的
	//加上synchronized关键字以后，对于getInstance()方法来说，它始终单线程来访问
	//没有充分利用上我们的计算机资源，造成资源的浪费
	public static synchronized Singleton2 getInstance() {
		if (single == null) {
			single = new Singleton2();
		}
		return single;  
	}
}
