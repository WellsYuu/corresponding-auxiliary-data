package com.xiangxue.ch7.safeclass;


/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：不可变的类
 */
public class ImmutableFinal {
	
	private final int a;
	private final int b;
	
	public ImmutableFinal(int a, int b) {
		super();
		this.a = a;
		this.b = b;
	}

	public int getA() {
		return a;
	}

	public int getB() {
		return b;
	}
	

}
