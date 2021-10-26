package com.enjoy.create.fatory.abstractFactory;

import com.enjoy.entity.Bag;
import com.enjoy.entity.Fruit;
import com.enjoy.entity.bag.BananaBag;
import com.enjoy.entity.fruit.Banana;

/**
 * 水果工厂
 */
public class BananaFactory extends AbstractFactory{

    @Override
    public Fruit getFruit() {
        return new Banana();
    }

    @Override
    public Bag getBag() {
        return new BananaBag();
    }
}
