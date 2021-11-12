package com.enjoy.action.template;

import com.enjoy.entity.Fruit;

import java.util.List;

/**
 * 模板方法模式
 * 购物车费用结算过程
 */
public class CashShopping extends ShoppingCart{


    public CashShopping(List<Fruit> products) {
        super(products);
    }

    @Override
    protected void pay(int money) {
        System.out.println("现金结算，假一罚十");
    }
}
