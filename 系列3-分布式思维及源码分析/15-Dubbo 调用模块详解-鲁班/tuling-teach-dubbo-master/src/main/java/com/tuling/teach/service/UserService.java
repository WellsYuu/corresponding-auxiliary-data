package com.tuling.teach.service;

import java.util.List;

/**
 * Created by Tommy on 2017/12/14.
 */
public interface UserService {
    public UserInfo getUserName(int userId,int age) throws  BusException;

	String searchUser(String name, UserInfo user);
}
