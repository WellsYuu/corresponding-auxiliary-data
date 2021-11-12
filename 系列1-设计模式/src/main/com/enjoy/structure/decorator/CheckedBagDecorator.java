package com.enjoy.structure.decorator;

import com.enjoy.entity.Bag;

public class CheckedBagDecorator extends BagDecorator {
    public CheckedBagDecorator(Bag bag) {
        super(bag);
    }

    public void pack() {
        super.pack();  //调用原有业务方法
        checked();  //打印防伪标识
    }

    //增加防伪标识
    public void checked() {
        System.out.println("------");
        System.out.println("打印上防伪标识");
    }
}
