package com.tuling.teach.service;

/**
 * Created by Tommy on 2017/12/14.
 */
public class UserServiceImpl implements UserService {
    public String getUserName(int userId) {
        return "hanmeimei"+userId;
    }
}
