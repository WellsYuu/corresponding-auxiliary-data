package com.xiangxue.ch03.dispatch;

public class Test {
	
	public static void say(int e) {
		System.out.println("int");
	}
	
	public static void say(String e) {
		System.out.println("String");
	}
	
	public static void main(String[] args) {
		say(null);
	}

}
