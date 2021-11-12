package com.xiangxue.ch03.dispatch;


/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：解析
 */
public class StaticDispatch{
	
	static abstract class Human{}
	static class Man extends Human{	}
	static class Woman extends Human{}
	
	public void sayHello(Human guy){
		System.out.println("hello,guy！");//1
	}
	public void sayHello(Man guy){
		System.out.println("hello,gentleman！");//2
	}
	public void sayHello(Woman guy){
		System.out.println("hello,lady！");//3
	}
	
	public static void main(String[]args){
		
		Human h1 = new Man();
		Human h2 = new Woman();
		
		StaticDispatch sr = new StaticDispatch();
		sr.sayHello(h1);
		sr.sayHello(h2);

		
	}
}
