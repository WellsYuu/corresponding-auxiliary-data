package com.xiangxue.ch1;

import com.xiangxue.tools.SleepTools;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：start和run方法的区别
 */
public class StartAndRun {
    public static class ThreadRun extends Thread{

        @Override
        public void run() {
            int i = 90;
            while(i>0){
            	SleepTools.ms(1000);
                System.out.println("I am "+Thread.currentThread().getName()
                		+" and now the i="+i--);
            }
        }
    }
    
    private static class User {
    	public void us() {
    		
    	}
    }

    public static void main(String[] args) {
    	ThreadRun beCalled = new ThreadRun();
    	beCalled.setName("BeCalled");
    	//beCalled.setPriority(newPriority);
    	beCalled.run();
    	
    	User user = new User();
    	user.us();
    	
    	//beCalled.start();
    }
}
