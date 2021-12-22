/**
 * 
 */
package com.tl.jvm.demo3;

/**
 * @author 张飞老师
 */
public class Demo {
	/**
	 * 
	<clinit> 初始化方法包含的代码为：
	
	public static int b = 10;
	static{
		a = 1;
	}
	
	public static int a = 10;
	public static int c = 10;
	
	 */
	public static int b = 10;
	static{
		a = 1;
//		System.out.println(a);// 不能读
		System.out.println(b);
	}
	
	public static int a = 10;
	public static int c = 10;
	

	public static void main(String[] args) {
		
		Demo demo = new Demo();
		System.out.println(demo.a);// ?
	}
}
