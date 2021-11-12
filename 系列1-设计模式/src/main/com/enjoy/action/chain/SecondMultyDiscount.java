package com.enjoy.action.chain;

/**
 * 第二单9折优惠
 */
public class SecondMultyDiscount extends MultyDiscount {
    public SecondMultyDiscount(MultyDiscount nextMultyDiscount) {
        super(nextMultyDiscount);
    }

    @Override
    public int calculate(int money) {
        System.out.println("第二单打9折");
        Double balance =  money * 0.9;

        return super.calculate(balance.intValue());
    }
}
