package com.enjoy.service.impl;

import com.enjoy.service.PayService;

public class PayServiceImpl implements PayService {

    @Override
    public boolean pay(long money) {
        System.out.println("付款成功");
        return true;
    }

    @Override
    public String cancelPay(long money) {
        System.out.println("退款成功");
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "1";
    }
}
