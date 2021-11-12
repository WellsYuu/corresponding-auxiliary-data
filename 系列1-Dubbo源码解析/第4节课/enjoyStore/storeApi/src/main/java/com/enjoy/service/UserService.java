package com.enjoy.service;

import com.enjoy.entity.UserEntiry;

public interface UserService {
    UserEntiry getDetail(String id);
    UserEntiry regist(UserEntiry user);
    UserEntiry recharge(String id, long money);
}
