package com.enjoy.action.observer.jdk;


import java.util.Observable;
import java.util.Observer;

public class Customer implements Observer {

    private String name;

    public Customer(String name){
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println(name + "购买青芒");
    }
}
