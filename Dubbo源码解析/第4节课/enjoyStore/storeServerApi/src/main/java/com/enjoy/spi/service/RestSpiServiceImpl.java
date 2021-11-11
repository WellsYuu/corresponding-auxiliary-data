package com.enjoy.spi.service;

public class RestSpiServiceImpl implements SpiService {

    @Override
    public void sayHello() {
        System.out.println("我是Rest服务实现");
    }
}
