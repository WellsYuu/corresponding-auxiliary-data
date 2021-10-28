package com.xiangxue.ch01;

public class StackAlloc {
	
	public static class User{
		public int id = 0;
		public String name = "";
		
		User(){
			
		}
	}
	
	public static User alloc() {
		User u = new User();
		u.id = 5;
		u.name = "mark";
		return u;
	}
	
	public static void main(String[] args) {
		long b = System.currentTimeMillis();
		for(int i=0;i<100000000;i++) {
			alloc();
		}
		long e = System.currentTimeMillis();
		System.out.println((e-b)+"ms");
	}
	

}
