package com.enjoy.create.fatory.fatoryMethod;

import com.enjoy.entity.Bag;
import com.enjoy.entity.bag.AppleBag;

/**
 * 工厂方法模式
 */
public class AppleBagFactory implements BagFactory{
    public Bag getBag(){
        return new AppleBag();
    }
}
