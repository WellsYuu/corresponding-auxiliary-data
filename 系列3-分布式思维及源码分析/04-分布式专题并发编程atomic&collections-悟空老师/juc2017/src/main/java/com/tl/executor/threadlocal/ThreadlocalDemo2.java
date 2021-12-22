package com.tl.executor.threadlocal;/*
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

import com.tl.executor.TljucUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadlocalDemo2 implements Runnable {
        // 反面教程 不能这样写哦
    static SimpleDateFormat simpleDateFormat=null;

    public static void main(String[] args) {
        TljucUtil.timeTasks(100,1,new ThreadlocalDemo2());
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100);
           //得写方法区内 SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=simpleDateFormat.parse("2017-11-28 22:45:11");
            Thread.sleep(100);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
