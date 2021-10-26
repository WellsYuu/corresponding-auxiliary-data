package com.enjoy.structure.decorator;

import com.enjoy.entity.Bag;

public class SpeedDecorator extends BagDecorator {
    public SpeedDecorator(Bag bag) {
        super(bag);
    }

    public void pack() {
        super.pack();  //调用原有业务方法
        speedy();
    }

    //快件加急
    public void speedy() {
        System.out.println("------");
        System.out.println("打上加急标识");
    }
}
