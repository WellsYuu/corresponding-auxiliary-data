package com.enjoy.action.strategy;

/**
 * 第二单9折优惠
 */
public class SecondDiscount implements Discount {
    @Override
    public int calculate(int money) {
        Double balance =  money * 0.9;
        return balance.intValue();
    }
}
