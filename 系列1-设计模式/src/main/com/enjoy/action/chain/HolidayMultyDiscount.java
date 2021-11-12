package com.enjoy.action.chain;

/**
 * 假日一律减5元
 */
public class HolidayMultyDiscount extends MultyDiscount {
    public HolidayMultyDiscount(MultyDiscount nextMultyDiscount) {
        super(nextMultyDiscount);
    }

    @Override
    public int calculate(int money) {
        if (money > 20){
            System.out.println("假日一律减5元");
            money = money - 5;
        }
        return super.calculate(money);
    }
}
