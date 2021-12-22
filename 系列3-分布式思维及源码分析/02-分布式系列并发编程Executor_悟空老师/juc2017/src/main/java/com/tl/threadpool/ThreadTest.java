package com.tl.threadpool;/*
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

public class ThreadTest {

    public static void main(String[] args) throws InterruptedException {
        Long start=System.currentTimeMillis();
        final Random random=new Random();
      final  List<Integer> list=new ArrayList<Integer>();
        for (int i=0;i<100000;i++){
          Thread thread=  new Thread(){
                @Override
                public void run() {
                      list.add(random.nextInt());

                }
            };
            thread.start();;
            thread.join();
        }
        System.out.println(System.currentTimeMillis()-start);
        System.out.println(list.size());

    }
}
