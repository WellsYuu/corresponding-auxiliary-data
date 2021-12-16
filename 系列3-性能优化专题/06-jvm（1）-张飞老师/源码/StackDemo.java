/**
 * 
 */
package com.edu.jvm.demo;

/**
 * @author 张飞老师
 */
public class StackDemo {

	private static int index = 1;
	public void test(){
		index ++;
		test();// 递归调用
	}
	// StackOverflowError
	/**
	 * 栈的深度
	 * -Xss
	 * 线程自己指定stack size
	 * 1M 
	 * @param args
	 */
	public static void main(String[] args) {
//		new Thread(group, target, name, stackSize)
		StackDemo demo = new  StackDemo();
		try {
			demo.test();
		} catch (Throwable e) {
			System.out.println("stack deep: " + index);
			e.printStackTrace();
		}
		demo.test();
	}
}
