package com.enjoy.create.fatory.fatoryMethod;

import com.enjoy.entity.Fruit;
import com.enjoy.entity.fruit.Orange;

/**
 * 工厂方法模式
 */
public class OrangeFactory implements FruitFactory{
    public Fruit getFruit(){
        return new Orange("Peter",80);
    }
}
