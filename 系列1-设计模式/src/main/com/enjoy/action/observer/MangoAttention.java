package com.enjoy.action.observer;

public class MangoAttention extends Attentions{

    @Override
    public void notifyObservers() {
        //遍历观察者集合，调用每一个顾客的购买方法
        for(Observer obs : observers) {
            obs.update();
        }
    }

    //芒果到货了
    public void perform(){
        this.notifyObservers();
    }
}
