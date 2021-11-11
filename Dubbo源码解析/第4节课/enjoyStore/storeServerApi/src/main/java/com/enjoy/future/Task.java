package com.enjoy.future;

import java.util.concurrent.Callable;

/**
 * Created by Peter on 11/16 016.
 * 单个银行查询
 */
public class Task implements Callable<Integer> {
    private String person;
    private String bank;

    public Task(String person,String bank){
        this.person = person;
        this.bank = bank;
    }


    /**
     * 银行欠款查询
     * @return
     * @throws Exception
     */
    @Override
    public Integer call() throws Exception {
        int time = getRandom(2000);
        Thread.currentThread().sleep(time);
        int money = getRandom(500);
        System.out.println(person+"欠款："+bank+"---"+money + "元,查询耗时："+time+"毫秒");
        return money;
    }

    public static int getRandom(int round){
        return (int) (1+Math.random()* round) ;
    }

    public static void main(String[] args) {
        System.out.println((int) (1+Math.random()* 1000));
    }
}
