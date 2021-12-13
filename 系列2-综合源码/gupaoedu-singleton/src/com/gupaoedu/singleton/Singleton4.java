package com.gupaoedu.singleton;

//懒汉式（静态内部类）
//这种写法，即解决安全问题，又解决了性能问题
//这个代码，没有浪费一个字
public class Singleton4 {
	//1、先声明一个静态内部类
	//private 私有的保证别人不能修改
	//static 保证全局唯一
	private static class LazyHolder {
		//final 为了防止内部误操作，代理模式，GgLib的代理模式
		private static final Singleton4 INSTANCE = new Singleton4();
	}
	//2、将默认构造方法私有化
	private Singleton4 (){}
	//相当于有一个默认的public的无参的构造方法，就意味着在代码中随时都可以new出来
		
	//3、同样提供静态方法获取实例
	//final 确保别人不能覆盖
	public static final Singleton4 getInstance() {  
		
		//方法中的逻辑，是要在用户调用的时候才开始执行的
		//方法中实现逻辑需要分配内存，也是调用时才分配的
		return LazyHolder.INSTANCE;
    }
	
//	static int a = 1;
//	//不管该class有没有实例化，static静态块总会在classLoader执行完以后，就加载完毕
//	static{
//		//静态块中的内容，只能访问静态属性和静态方法
//		//只要是静态方法或者属性，直接可以用Class的名字就能点出来
//		Singleton4.a = 2;
//		//JVM 内存中的静态区，这一块的内容是公共的 
//	}
}

//我们所写的所有的代码，在java的反射机制面前，都是裸奔的
//反射机制是可以拿到private修饰的内容的
//我们可以理解成即使加上private也不靠谱（按正常套路出牌，貌似可以）


//类装载到JVM中过程
//1、从上往下(必须声明在前，使用在后)
//先属性、后方法
//先静态、后动态
