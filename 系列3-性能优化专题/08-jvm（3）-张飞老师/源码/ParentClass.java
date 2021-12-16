/**
 * 
 */
package com.tl.jvm.demo3;

/**
 * @author 张飞老师
 */
public class ParentClass {

	public static int age = 20;
	
	static{
		System.out.println("ParentClass init...");
	}
	
	public static void testStaticMethod(){
		System.out.println("ParentClass.testStaticMethod...");
	}
	
	public void testCommonMethod(){
		System.out.println("ParentClass.testCommonMethod...");
	}
}
