package com.xiangxue.ch7.tranfer;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：用户账户的实体类
 */
public class UserAccount {
    //private int id;
    private final String name;//账户名称
    private int money;//账户余额

    //显示锁
    private final Lock lock = new ReentrantLock();

    public Lock getLock() {
        return lock;
    }

    public UserAccount(String name, int amount) {
        this.name = name;
        this.money = amount;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return money;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "name='" + name + '\'' +
                ", money=" + money +
                '}';
    }

    //转入资金
    public void addMoney(int amount){
        money = money + amount;
    }

    //转出资金
    public void flyMoney(int amount){
        money = money - amount;
    }
}
