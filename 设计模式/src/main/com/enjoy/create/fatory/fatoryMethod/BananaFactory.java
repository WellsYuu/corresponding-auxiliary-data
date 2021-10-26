package com.enjoy.create.fatory.fatoryMethod;

import com.enjoy.entity.fruit.Banana;
import com.enjoy.entity.Fruit;

/**
 * 工厂方法模式
 */
public class BananaFactory implements FruitFactory{
    public Fruit getFruit(){
        return new Banana();
    }
}
