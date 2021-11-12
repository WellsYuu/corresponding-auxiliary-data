package com.enjoy.action.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Attentions {
    //关注客户列表
    protected List<Observer> observers = new ArrayList();

    //关注顾客
    public void add(Observer observer) {
        observers.add(observer);
    }

    //取消关注
    public void remove(Observer observer) {
        observers.remove(observer);
    }

    //发通知
    public abstract void notifyObservers();
}
