package com.enjoy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.enjoy.service.VipUserService;

@Service
public class VipUserServiceImpl implements VipUserService {

    public String getVipDetail(String id) {
        String ret = super.getClass().getName()+"被调用一次："+System.currentTimeMillis();
        System.out.println(ret);
        return ret;
    }
}
