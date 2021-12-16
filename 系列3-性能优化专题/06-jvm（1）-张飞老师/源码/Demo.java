/**
 * 
 */
package com.edu.jvm.demo;

/**
 * @author 张飞老师
 */
public class Demo {
	/**
	 * 
	<clinit> 初始化方法
	public static int a = 10;
	static{
		a = a+1;
		System.out.println(a);
	}
	Class
	 */
	
	static{
//		a = a +  1;
		a = 1;
//		System.out.println(a);
		// 不能读，可以写
	}
	
	public static int a = 10;
	

	public static void main(String[] args) {
		
		Demo demo = new Demo();
		System.out.println(demo.a);// ?
	}
}
