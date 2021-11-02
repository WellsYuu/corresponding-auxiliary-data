package com.xiangxue.ch4.condition;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：测试Lock和Condition实现等待通知
 */
public class TestCond {
    private static ExpressCond express = new ExpressCond(0,ExpressCond.CITY);

    /*检查里程数变化的线程,不满足条件，线程一直等待*/
    private static class CheckKm extends Thread{
        @Override
        public void run() {
        	express.waitKm();
        }
    }

    /*检查地点变化的线程,不满足条件，线程一直等待*/
    private static class CheckSite extends Thread{
        @Override
        public void run() {
        	express.waitSite();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<3;i++){
            new CheckSite().start();
        }
        for(int i=0;i<3;i++){
            new CheckKm().start();
        }

        Thread.sleep(1000);
        express.changeKm();//快递里程变化
    }
}
