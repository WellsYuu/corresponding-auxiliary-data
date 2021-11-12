package com.enjoy.action.template;

import com.enjoy.entity.Fruit;

import java.util.List;

/**
 * 模板方法模式
 * 购物车费用结算过程
 */
public class CartShopping extends ShoppingCart{


    public CartShopping(List<Fruit> products) {
        super(products);
    }

    @Override
    protected void pay(int money) {
        System.out.println("会员卡结算，立减10，金额："+ (money - 10)+",增加积分："+10*money);
    }
}
