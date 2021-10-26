package com.enjoy.create.fatory.abstractFactory;

import com.enjoy.entity.Bag;
import com.enjoy.entity.Fruit;
import com.enjoy.entity.bag.OrangeBag;
import com.enjoy.entity.fruit.Orange;

/**
 * 水果工厂
 */
public class OrangeFactory extends AbstractFactory{

    @Override
    public Fruit getFruit() {
        return new Orange("Peter",50);
    }

    @Override
    public Bag getBag() {
        return new OrangeBag();
    }
}
