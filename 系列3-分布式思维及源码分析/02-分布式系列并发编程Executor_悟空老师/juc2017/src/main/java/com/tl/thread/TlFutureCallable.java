package com.tl.thread;/*
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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class TlFutureCallable  implements Callable<String> {
    @Override
    public String call() throws Exception {
        StringBuffer sb=new StringBuffer();
        for (int i =0;i<3;i++){
            sb.append("悟空是只猴子").append(i);
        }

        return sb.toString();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask task=new FutureTask(new TlFutureCallable(){

        });
        new Thread(task).start();
        task.cancel(true);
        System.out.println(task.get());


    }
}
