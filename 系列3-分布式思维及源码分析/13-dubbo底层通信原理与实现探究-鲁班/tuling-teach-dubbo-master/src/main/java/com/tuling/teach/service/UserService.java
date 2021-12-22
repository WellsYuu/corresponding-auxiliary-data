package com.tuling.teach.service;

/**
 * Created by Tommy on 2017/12/14.
 */
public interface UserService {
    public UserInfo getUserName(int userId,int age) throws  BusException;
}
