package com.tl.executor.collections.queue;/*
 * ━━━━━━如来保佑━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　┻　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━永无BUG━━━━━━
 * 图灵学院-悟空老师
 * www.jiagouedu.com
 * 悟空老师QQ：245553999
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class QueueExample {

    public static void main(String[] args) throws InterruptedException {
    /*    BlockingQueue queue=new ArrayBlockingQueue(2);
        queue.add("a");
        queue.add("b");
        queue.put("c");
       // queue.add("b");
        queue.offer("c");
        System.out.println(queue.peek());
        System.out.println(queue.size());
        System.out.println(queue.remove());
        System.out.println(queue.poll());
        //System.out.println(queue.take());
        System.out.println("---"+queue.size());*/
        /***这个是上课演示失败的，
         * 其中问题是：SynchronousQueue队列是需要有take（取）时 才能往里面add（放）。
         * 总结:必须有人要才能放，否则就放不进去**/
       final SynchronousQueue queue=new SynchronousQueue();
       // queue.add("a");
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);//t1线程必须在t2前 才能 符合其队列要求。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                queue.add("a");
               // queue.add("b");这会报 Queue full 只能存一个

            }
        });
        t1.start();
        t2.start();
        //  System.out.println(queue.peek());

    }
}

