package com.xiangxue.ch1;

import java.util.concurrent.ExecutionException;

/**
 * @author Mark老师 享学课堂 https://enjoy.ke.qq.com
 *
 *  类说明：守护线程的使用和守护线程中的finally语句块
 */
public class DaemonThread {
	
	private static class UseThread extends Thread {
		@Override
		public void run() {
			try {
				while (!isInterrupted()) {
					System.out.println(Thread.currentThread().getName()
							+ " I am extends Thread.");
				}
				System.out.println(Thread.currentThread().getName() 
						+ " interrupt flag is " + isInterrupted());
			} finally {
				System.out.println("...........finally");
			}
		}
	}

	public static void main(String[] args) throws InterruptedException, 
		ExecutionException {
		UseThread useThread = new UseThread();
		useThread.setDaemon(true);
		useThread.start();
		Thread.sleep(5);
		//useThread.interrupt();
	}
}
