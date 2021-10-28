package com.xiangxue.ch01;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：
 */
public class SimpleHeap {
	
	private int id;

	public SimpleHeap(int id) {
		super();
		this.id = id;
	}
	
	public void print() {
		System.out.println("My id is "+id);
	}
	
	public static void main(String[] args) {
		SimpleHeap s1 = new SimpleHeap(1);
		SimpleHeap s2 = new SimpleHeap(2);
		s1.print();
		s2.print();
	}

}
