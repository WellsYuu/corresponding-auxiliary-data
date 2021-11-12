package com.enjoy.dao.impl;

import com.enjoy.dao.UserDao;
import com.enjoy.entity.UserEntiry;
import org.springframework.stereotype.Service;

@Service
public class UserDaoImpl implements UserDao {
    @Override
    public UserEntiry getDetail(String id) {
        UserEntiry user = new UserEntiry();
        user.setId("U001");
        user.setName("李四");
        user.setAddress("广州");
        user.setBalance(5000);
        return user;
    }

    @Override
    public UserEntiry regist(UserEntiry user) {
        user = new UserEntiry();
        user.setId("U001");
        user.setName("张三");
        user.setAddress("北京");
        user.setBalance(5000);
        return user;
    }

    @Override
    public UserEntiry recharge(String id, long money) {
        UserEntiry user = new UserEntiry();
        user.setId("U001");
        user.setName("张三");
        user.setAddress("北京");
        user.setBalance(money);
        System.out.println("恭喜你，充值"+money+"元成功！");
        return user;
    }
}
