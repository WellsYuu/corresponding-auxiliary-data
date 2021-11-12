package com.enjoy.service.impl;

import com.enjoy.dao.UserDao;
import com.enjoy.entity.UserEntiry;
import com.enjoy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public UserEntiry getDetail(String id) {
        System.out.println(super.getClass().getName()+"被调用一次："+System.currentTimeMillis());
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userDao.getDetail(id);
    }

    @Override
    public UserEntiry regist(UserEntiry user) {
        return null;
    }

    @Override
    public UserEntiry recharge(String id, long money) {
        return null;
    }

}
