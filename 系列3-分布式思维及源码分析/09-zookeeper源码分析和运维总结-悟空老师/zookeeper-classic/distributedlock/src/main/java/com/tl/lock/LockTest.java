/*
package com.tl;*/
/*
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
 *//*


import java.text.SimpleDateFormat;
import java.util.Date;

public class LockTest {

    public static void main(String[] args) {
        final LockTest aBc=new LockTest();
        DistributedLock lock   = new DistributedLock("192.168.0.101:2181,192.168.0.102:2181,192.168.0.104:2181","lock");
        lock.lock();
            TljucUtil.timeTasks(10, 10, new Runnable() {
                @Override
                public void run() {
                    System.out.println(aBc.getOrderNo());
                }
            });

        if(lock != null)
            lock.unlock();
    }
    static  int num;

      public String getOrderNo() {
          try {
              Thread.sleep(100);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
          SimpleDateFormat simpleDateFormat=  new SimpleDateFormat("YYYYmmDD");
        return simpleDateFormat.format(new Date())+num++;

    }
}
*/
