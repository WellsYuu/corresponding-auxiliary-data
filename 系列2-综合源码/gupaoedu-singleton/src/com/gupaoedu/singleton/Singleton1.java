package com.gupaoedu.singleton;

//懒汉式单例类.在第一次调用的时候实例化自己
public class Singleton1 {
	//1、第一步先将构造方法私有化
	private Singleton1() {}
	//2、然后声明一个静态变量保存单例的引用
	private static Singleton1 single = null;
	//3、通过提供一个静态方法来获得单例的引用
	//不安全的
	public static Singleton1 getInstance() {
		if (single == null) {
			single = new Singleton1();
		}
		return single;
	}
}
