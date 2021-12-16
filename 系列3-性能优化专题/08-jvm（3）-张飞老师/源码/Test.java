/**
 * 
 */
package com.tl.jvm.demo3;

/**
 * -XX:+TraceClassLoading
 * @author 张飞老师
 */
public class Test {
	
//	static{
//		System.out.println("Test.init...");
//	}

	public static void main(String[] args) throws ClassNotFoundException {
//		System.out.println("main...");
		
//		System.out.println(SubClass.age);// ParentClass init...
//		System.out.println(SubClass.age=10);// ParentClass init...
		
//		new SubClass();// ParentClass init...; SubClass init...
		
//		ParentClass p = new SubClass();// ParentClass init...; SubClass init...
		
//		ParentClass.testStaticMethod();//ParentClass init...
//		SubClass.testStaticMethod();// ParentClass init...; SubClass init...
		
//		ParentClass[] pc = new ParentClass[10];// 不会初始化
//		ParentClass[] pcs = new SubClass[10];// 不会初始化
		
//		pc[0] = new SubClass();// ParentClass init...; SubClass init...
//		pcs[0] = new SubClass();// ParentClass init...; SubClass init... 《》 new SubClass()
		
		
		SubClass[] sc = new SubClass[10];
//		sc[1].testStaticMethod();// ParentClass init...; SubClass init...
//		sc[1].testCommonMethod();// java.lang.NullPointerException
		
		// 常量，不会对类的初始化产生任何影响
		
		Class<?> clazz = Class.forName("com.tl.jvm.demo3.ParentClass");// ParentClass init...
		
	}
}
