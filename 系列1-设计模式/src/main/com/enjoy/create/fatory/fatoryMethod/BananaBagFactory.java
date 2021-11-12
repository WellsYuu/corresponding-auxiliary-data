package com.enjoy.create.fatory.fatoryMethod;

import com.enjoy.entity.Bag;
import com.enjoy.entity.bag.BananaBag;

/**
 * 工厂方法模式
 */
public class BananaBagFactory implements BagFactory{
    public Bag getBag(){
        return new BananaBag();
    }
}
