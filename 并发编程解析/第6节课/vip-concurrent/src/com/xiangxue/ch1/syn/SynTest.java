package com.xiangxue.ch1.syn;

import com.xiangxue.tools.SleepTools;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：
 */
public class SynTest {
	
	private volatile int age = 100000;//初始100000
	
//	public int getAge() {
//		return age;
//	}
	
	public void setAge() {
		age = age+20;
	}
	
    private static class TestThread extends Thread{
    	
    	private SynTest synTest;
    	
    	public TestThread(SynTest synTest,String name) {
    		super(name);
    		this.synTest = synTest;
		}
    	
    	@Override
	    public void run() {
    		for(int i=0;i<100000;i++) {//递增100000
    			synTest.test();
    		}
			System.out.println(Thread.currentThread().getName()
					+" age =  "+synTest.getAge());
	    }
    }
    
	public synchronized void test() {
		age++;
		test2();
	}
	
	public synchronized void test2() {
		{
			age--;
		}
	}
	
	public int getAge() {
		return age;
	}
	

	public static void main(String[] args) throws InterruptedException {
		SynTest synTest = new SynTest();
		Thread endThread = new TestThread(synTest,"endThread");
		endThread.start();
		for(int i=0;i<100000;i++) {//递减100000
			synTest.test2();
		}
		System.out.println(Thread.currentThread().getName()
				+" age =  "+synTest.getAge());		

	}
}
