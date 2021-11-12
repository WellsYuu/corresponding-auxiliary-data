package com.xiangxue.ch6;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.xiangxue.tools.SleepTools;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：线程池的使用
 */
public class UseThreadPool {
	//工作线程
    static class Worker implements Runnable
    {
        private String taskName;
        private Random r = new Random();

        public Worker(String taskName){
            this.taskName = taskName;
        }

        public String getName() {
            return taskName;
        }

        @Override
        public void run(){
            System.out.println(Thread.currentThread().getName()
            		+" process the task : " + taskName);
            SleepTools.ms(r.nextInt(100)*5);
        }
    }
    
    static class CallWorker implements Callable<String>{
    	
        private String taskName;
        private Random r = new Random();

        public CallWorker(String taskName){
            this.taskName = taskName;
        }

        public String getName() {
            return taskName;
        }    	

		@Override
		public String call() throws Exception {
            System.out.println(Thread.currentThread().getName()
            		+" process the task : " + taskName);
            return Thread.currentThread().getName()+":"+r.nextInt(100)*5;
		}
    	
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException
    {
//    	ExecutorService pool = new ThreadPoolExecutor(2,4,3,TimeUnit.SECONDS,
//    			new ArrayBlockingQueue<Runnable>(10),
//    			new ThreadPoolExecutor.DiscardOldestPolicy());
    	ExecutorService pool = Executors.newCachedThreadPool();//其实是比较少
    	for(int i=0;i<6;i++) {
    		Worker worker = new Worker("worker_"+i);
    		pool.execute(worker);
    	}
    	for(int i=0;i<6;i++) {
    		CallWorker callWorker = new CallWorker("callWorker_"+i);
    		Future<String> result = pool.submit(callWorker);
    		System.out.println(result.get());
    	}
    	pool.shutdown();
    }
}
