package com.enjoy.create.fatory.abstractFactory;

import com.enjoy.entity.Bag;
import com.enjoy.entity.Fruit;
import com.enjoy.entity.bag.AppleBag;
import com.enjoy.entity.fruit.Apple;

/**
 * 水果工厂
 */
public class AppleFactory extends AbstractFactory{

    @Override
    public Fruit getFruit() {
        return new Apple();
    }

    @Override
    public Bag getBag() {
        return new AppleBag();
    }
}
