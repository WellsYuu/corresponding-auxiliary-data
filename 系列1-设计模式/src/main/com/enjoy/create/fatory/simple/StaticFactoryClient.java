package com.enjoy.create.fatory.simple;

import com.enjoy.entity.Fruit;

/**
 * Peter吃苹果
 */
public class StaticFactoryClient {


    public static void main(String[] args) {
//        peterdo();
        jamesdo();
//        lisondo();
    }

    //Peter自己吃水果
    public static void peterdo(){
        Fruit fruit = StaticFactory.getFruit(StaticFactory.TYPE_BANANA);
        fruit.draw();
        //。。。直接啃着吃，吃掉了
        System.out.println("-----------------");
    }

    //送给james，切开吃
    public static void jamesdo(){
        Fruit fruit = StaticFactory.getFruitBanana();
        fruit.draw();
        //。。。切开吃
        System.out.println("-----------------");
    }

    //送给lison榨汁喝
    public static void lisondo(){
        Fruit fruit = StaticFactory.getFruit(StaticFactory.TYPE_APPLE);
        fruit.draw();
        //。。。榨汁动作
        System.out.println("-----------------");
    }
}
