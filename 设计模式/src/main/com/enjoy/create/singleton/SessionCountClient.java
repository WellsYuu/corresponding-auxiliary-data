package com.enjoy.create.singleton;

/**
 * 统计在线人数
 */
public class SessionCountClient {

    public static void main(String[] args) {
        //营业开门
        SessionCount count = SessionCount.getInstance();
        count.plus();

        count.showMessage();

        SessionCount count1 = SessionCount.getInstance();
        count1.plus();

        count1.showMessage();

        //出去一个
        SessionCount count2 = SessionCount.getInstance();
        count2.decrease();
        count2.showMessage();
    }
}
