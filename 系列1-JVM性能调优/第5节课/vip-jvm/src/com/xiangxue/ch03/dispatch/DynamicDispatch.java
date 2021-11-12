package com.xiangxue.ch03.dispatch;

import com.xiangxue.ch03.dispatch.StaticDispatch.Human;
import com.xiangxue.ch03.dispatch.StaticDispatch.Man;
import com.xiangxue.ch03.dispatch.StaticDispatch.Woman;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：动态分派
 */
public class DynamicDispatch {
	static abstract class Human{
		protected abstract void sayHello();
	}
	
	static class Man extends Human{

		@Override
		protected void sayHello() {
			System.out.println("hello,gentleman！");
			
		}	
	}
	static class Woman extends Human{

		@Override
		protected void sayHello() {
			System.out.println("hello,lady！");
			
		}
	}
	
	public static void main(String[]args){
		Human h1 = new Man();
		Human h2 = new Woman();
		h1.sayHello();
		h2.sayHello();


		
	}
}
