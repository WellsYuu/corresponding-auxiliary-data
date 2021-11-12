package com.enjoy.service;

public interface PayService {
    boolean pay(long money);
    String cancelPay(long money);
}
