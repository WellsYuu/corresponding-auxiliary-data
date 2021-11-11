package com.enjoy.spi.service;

public class RmiSpiServiceImpl implements SpiService {

    @Override
    public void sayHello() {
        System.out.println("我是rmi服务实现");
    }
}
