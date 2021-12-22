/**
 * 
 */
package com.edu.jvm.demo2;

/**
 * @author 张飞老师
 */
public class SynchronizedDemo {
	// 共享变量
	private boolean flag = false;
	private int a = 1;
	private int result = 0;
	// 写操作
	public synchronized void write(){
		// 这里有可能会发生指令重排
		flag = true;// 1.1
		a = 2; // 1.2
	}
	/*
	 *1.1 1.2 2.1 2.2 -> 6
	 *1.2 1.1 2.1 2.2 -> 6
	 *1.2 2.1 2.2 1.1 -> 0
	 */
	// 读操作
	public synchronized void read(){
		if(flag){// 2.1
			result = a * 3; // 2.2
		}
		System.out.println("result: " + result);
	}
	
	private class ReadWriteThread extends Thread{
		private boolean flag;
		public ReadWriteThread(boolean flag){
			this.flag = flag;
		}
		
		@Override
		public void run() {
			if(flag){
				write();
			}else{
				read();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		SynchronizedDemo demo = new SynchronizedDemo();
		demo.new ReadWriteThread(true).start();// 写
		Thread.sleep(10);
		demo.new ReadWriteThread(false).start();// 读
		// CPU
	}
}
