package com.xiangxue.ch03.classinit;

public class SuperClazz {
	
	static {
		System.out.println("SuperClass init!");
	}
	
	public static int value = 123;//0
	public static final String HELLOWORLD = "hello,world";
	public static final int WHAT = value;

}
