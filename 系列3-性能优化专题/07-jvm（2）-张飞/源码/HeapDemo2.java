/**
 * 
 */
package com.edu.jvm.demo2;

/**
 * @author 张飞老师
 */
public class HeapDemo2 {

	public static void main(String[] args) {
		byte [] b = null;
		// 10M
		for (int i = 0; i < 11; i++) {
			b = new byte[1*1024*1024];// 1M
		}
	}
}
