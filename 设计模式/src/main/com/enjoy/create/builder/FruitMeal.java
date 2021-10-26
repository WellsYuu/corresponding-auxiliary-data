package com.enjoy.create.builder;


import com.enjoy.entity.fruit.Apple;
import com.enjoy.entity.fruit.Banana;
import com.enjoy.entity.fruit.Orange;

//创建一个水果套餐Meal类
public class FruitMeal {

    private Apple apple;//苹果--价格
    private Banana banana;//香蕉价格
    private Orange orange;    //桔子价格
    private int discount;//折扣价

    private int totalPrice;//套餐总价

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setApple(Apple apple) {
        this.apple = apple;
    }

    public void setBanana(Banana banana) {
        this.banana = banana;
    }

    public void setOrange(Orange orange) {
        this.orange = orange;
    }

    public int cost(){
        return this.totalPrice;
    }

    public void init() {
        if (null != apple){
            totalPrice += apple.price();
        }
        if (null != orange){
            totalPrice += orange.price();
        }
        if (null != banana){
            totalPrice += banana.price();
        }
        if (totalPrice > 0){
            totalPrice -= discount;
        }
    }

    public void showItems() {
        System.out.println("totalPrice：" + totalPrice);
    }
}

