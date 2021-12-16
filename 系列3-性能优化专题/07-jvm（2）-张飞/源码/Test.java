/**
 * 
 */
package com.edu.jvm.demo2;

/**
 * @author 张飞老师
 */
public class Test {

	public static void abc(){
		byte[] b = new byte[2];
		b[0] = 1;
	}
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			abc();
		}
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
}
