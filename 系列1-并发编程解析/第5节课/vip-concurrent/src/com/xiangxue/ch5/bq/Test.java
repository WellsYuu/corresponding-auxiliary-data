package com.xiangxue.ch5.bq;

import java.util.concurrent.DelayQueue;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：延时队列测试程序
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {

        //每隔500毫秒，打印个数字
        for(int i=1;i<15;i++){
            Thread.sleep(500);
            System.out.println(i*500);
        }
    }
}
