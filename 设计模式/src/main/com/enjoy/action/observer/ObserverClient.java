package com.enjoy.action.observer;

/**
 * 观察者模式
 * 顾客关注了芒果，降价时通知他们
 */
public class ObserverClient {


    public static void main(String[] args) {
        MangoAttention attentions = new MangoAttention();//目标

        attentions.add(new CustomerObserver("deer"));
        attentions.add(new CustomerObserver("james"));
        attentions.add(new CustomerObserver("lison"));
        attentions.add(new CustomerObserver("mark"));

        //到货
        attentions.perform();

    }


}
