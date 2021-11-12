package com.enjoy.entity.fruit;

import com.enjoy.action.visit.Visit;
import com.enjoy.entity.Fruit;
import com.enjoy.entity.bag.AppleBag;

/**
 * 桔子
 * Created by Peter on 10/9 009.
 */
public class Apple implements Fruit {
    private int price = 100;

    public Apple(){

    }

    public Apple(int price){
        this.price = price;
    }

    public void pack(AppleBag bag){
        bag.pack();
    }

    @Override
    public int price() {
        return price;
    }

    @Override
    public void draw() {
        System.out.print("苹果红富士");
    }

    public void setPrice(int price) {
        this.price = price;
    }




    public int accept(Visit visit){
        return visit.sell(this);//指针可以传递真实类型
    }

    public static void main(String[] args) {
        Visit visit = new Visit();
        new Apple().accept(visit);
    }
}
