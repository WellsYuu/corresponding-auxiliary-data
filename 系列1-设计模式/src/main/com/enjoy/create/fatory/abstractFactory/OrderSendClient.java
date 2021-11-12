package com.enjoy.create.fatory.abstractFactory;

import com.enjoy.entity.Bag;
import com.enjoy.entity.Fruit;

/**
 * 抽象工厂模式测试
 * 按订单发送货品给客户
 * Created by Peter on 10/8 008.
 */
public class OrderSendClient {
    public static void main(String[] args){
        sendFruit();
    }

    public static void sendFruit(){
        //初始化工厂
        AbstractFactory factory = new AppleFactory();//spring使用注入方式

        //得到水果
        Fruit fruit = factory.getFruit();
        fruit.draw();
        //得到包装
        Bag bag = factory.getBag();
        bag.pack();
        //以下物流运输业务。。。。

    }



}
