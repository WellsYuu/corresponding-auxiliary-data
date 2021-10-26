package com.enjoy.create.fatory.abstractFactory;

import com.enjoy.entity.Bag;
import com.enjoy.entity.Fruit;

/**
 * 抽象水果工厂
 */
public abstract class AbstractFactory {

    public abstract  Fruit getFruit();

    public abstract Bag getBag();

}
