package com.enjoy.action.chain;

/**
 * 满减
 */
public class FullMultyDiscount extends MultyDiscount {
    public FullMultyDiscount(MultyDiscount nextMultyDiscount) {
        super(nextMultyDiscount);
    }

    @Override
    public int calculate(int money) {
        if (money > 200){
            System.out.println("优惠满减20元");
            money = money - 20;
        }

        return super.calculate(money);
    }
}
