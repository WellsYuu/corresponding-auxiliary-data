package com.tl.executor.threadpool;/*
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class ThreadPoolTest {

    public static void main(String[] args) throws InterruptedException {
        Long start=System.currentTimeMillis();
        final Random random=new Random();
        final List<Integer> list=new ArrayList<Integer>();
       ExecutorService executorService=Executors.newSingleThreadExecutor();

        //这块代码是监控
      // new Thread(new MonitorThreadPoolUtil((ThreadPoolExecutor) executorService,1)).start();
        for (int i=0;i<100000;i++){

            executorService.submit(new Runnable() {
                @Override
                public void run() {

                        list.add(random.nextInt());

                }
            });

        }
        executorService.shutdown();
       executorService.awaitTermination(1, TimeUnit. DAYS);
        //System.out.println(System.currentTimeMillis()-start);
        System.out.println(list.size());


    }
}
