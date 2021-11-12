package com.enjoy.service.impl;

import com.enjoy.service.RmiEnjoyService;

public class RmiEnjoyServiceImpl implements RmiEnjoyService {

    @Override
    public String process(String msg) {
        String ret = super.getClass().getName()+"被调用一次："+System.currentTimeMillis();
        System.out.println(ret);
        return ret;
    }
}
