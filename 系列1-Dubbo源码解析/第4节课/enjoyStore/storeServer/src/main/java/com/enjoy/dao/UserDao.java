package com.enjoy.dao;

import com.enjoy.entity.UserEntiry;

public interface UserDao {
    UserEntiry getDetail(String id);
    UserEntiry regist(UserEntiry user);
    UserEntiry recharge(String id, long money);
}
