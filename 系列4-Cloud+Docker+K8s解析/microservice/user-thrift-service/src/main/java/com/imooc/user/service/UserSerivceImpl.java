package com.imooc.user.service;

import com.imooc.thrift.user.UserInfo;
import com.imooc.thrift.user.UserService;
import com.imooc.user.mapper.UserMapper;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Michael on 2017/10/28.
 */
@Service
public class UserSerivceImpl implements UserService.Iface {


    @Autowired
    private UserMapper userMapper;

    @Override
    public UserInfo getUserById(int id) throws TException {

        return userMapper.getUserById(id);
    }

    @Override
    public UserInfo getTeacherById(int id) throws TException {
        return userMapper.getTeacherById(id);
    }

    @Override
    public UserInfo getUserByName(String username) throws TException {
        return userMapper.getUserByName(username);
    }

    @Override
    public void regiserUser(UserInfo userInfo) throws TException {
        userMapper.registerUser(userInfo);
    }
}
