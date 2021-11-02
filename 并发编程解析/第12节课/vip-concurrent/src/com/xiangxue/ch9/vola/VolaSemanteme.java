package com.xiangxue.ch9.vola;

public class VolaSemanteme {
	int a = 0;
	volatile boolean flag = false;

	public void init() {
		a = 1; 
		flag = true; 
		//.......
	}

	public void use() {
		if (flag) { 
			int i = a * a; 
		}
		//.......
	}
}
