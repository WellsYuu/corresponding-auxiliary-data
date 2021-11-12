package com.enjoy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.enjoy.dao.UserDao;
import com.enjoy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public String getDetail(String id) {
        System.out.println(super.getClass().getName()+"被调用一次："+System.currentTimeMillis());
        return userDao.getDetail(id);
    }

}
