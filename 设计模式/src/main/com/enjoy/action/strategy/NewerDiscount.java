package com.enjoy.action.strategy;

/**
 * 首次购
 */
public class NewerDiscount implements Discount {
    @Override
    public int calculate(int money) {
        if (money > 100){
            return money - 20;
        }
        return money;
    }
}
