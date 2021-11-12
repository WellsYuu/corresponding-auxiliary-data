package com.enjoy.action.observer.jdk;

/**
 * 观察者模式
 * 顾客关注了芒果商品，到货时通知他们
 */
public class JdkObserverClient {


    public static void main(String[] args) {
        Mango attentions = new Mango("芒果");

        attentions.addObserver(new Customer("deer"));
        attentions.addObserver(new Customer("james"));
        attentions.addObserver(new Customer("lison"));
        attentions.addObserver(new Customer("mark"));

        attentions.perform();

    }


}
