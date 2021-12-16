/**
 * 
 */
package com.edu.jvm.demo2;

/**
 * @author 张飞老师
 */
public class ThreadDemo extends Thread{
	private volatile boolean stop = false;// true表示停止 false表示继续运行
	@Override
	public void run() {
		System.out.println(this.currentThread().getName());
		int i = 0;
		while(!stop){
			i ++;
		}
		System.out.println(i);
	}
	
	public void stopMe(){
		System.out.println(this.currentThread().getName());
		this.stop = true;
	}

	public static void main(String[] args) throws InterruptedException {
		ThreadDemo t = new ThreadDemo();
		t.start();// 执行run方法  子线程
		Thread.sleep(1000);
		t.stopMe();// 停止  主线程 Main
	}
}
