package com.enjoy.create.fatory.fatoryMethod;

import com.enjoy.entity.fruit.Apple;
import com.enjoy.entity.Fruit;

/**
 * 工厂方法模式
 */
public class AppleFactory implements FruitFactory{
    public Fruit getFruit(){
        return new Apple();
    }
}
