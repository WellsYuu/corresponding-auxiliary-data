package com.xiangxue.ch01;

public class StackOOM {
	
	private int stackLength = 1;
	private void diGui(int x,String y) {
		stackLength++;
		diGui(x,y);
	}
	
	public static void main(String[] args) {
		StackOOM oom = new StackOOM();
		try {
			oom.diGui(12,"Mark");
		} catch (Throwable e) {
			System.out.println("stackLength = "+oom.stackLength);
			e.printStackTrace();
		}
	}

}
