package com.gupaoedu.vip.factory.func;

public class FactoryTest {

	public static void main(String[] args) {
		
		//工厂方法模式
		//各个产品的生产商，都拥有各自的工厂
		//生产工艺，生成的高科技程度都是不一样的
		Factory factory = new AudiFactory();
		System.out.println(factory.getCar());
		
		//需要用户关心，这个产品的生产商
		factory = new BmwFactory();
		System.out.println(factory.getCar());
		
		//增加的代码的使用复杂度
		
		
		//抽象工厂模式
		
	}
	
}
