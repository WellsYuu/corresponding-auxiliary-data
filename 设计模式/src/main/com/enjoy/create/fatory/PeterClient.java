package com.enjoy.create.fatory;

import com.enjoy.entity.Fruit;
import com.enjoy.entity.fruit.Apple;
import com.enjoy.entity.fruit.Orange;

/**
 * Peter吃苹果
 * 自己栽，自己摘
 */
public class PeterClient {

    //Peter自己吃水果
    public static void main(String[] args) {
        peterdo();
        jamesdo();
        lisondo();
    }

    //Peter自己吃水果
    public static void peterdo(){
        Fruit fruit = new Apple();
        fruit.draw();
        //。。。直接啃着吃，吃掉了
        System.out.println("-----------------");
    }

    //送给james，切开吃
    public static void jamesdo(){
        Fruit fruit = new Apple();
        fruit.draw();
        //。。。切开吃
        System.out.println("-----------------");
    }

    //送给lison榨汁喝
    public static void lisondo(){
        Fruit fruit = new Orange("peter",100);
        fruit.draw();
        //。。。榨汁运作
        System.out.println("-----------------");
    }

}
