/**
 * 
 */
package com.edu.jvm.demo2;

/**
 * @author 张飞老师
 */
public class HeapDemo1 {

	public static void main(String[] args) {
		byte[] b = new byte[1*1024*1024];// 1M
		// -Xmx20m  -Xms8m
		//xmx 可使用的总内存
		System.out.println(Runtime.getRuntime().maxMemory()/1024.0/1024 + "MB");
		
		// free men 堆剩余的内存
		System.out.println(Runtime.getRuntime().freeMemory()/1024.0/1024 + "MB");
		
		// total mem  堆已经分配的内存
		System.out.println(Runtime.getRuntime().totalMemory()/1024.0/1024 + "MB");
	}
}
