package com.tuling.teach.service;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommy on 2017/12/14.
 */
public class UserServiceImpl implements UserService {

    public UserInfo getUserName(int userId, int age) throws BusException {
        return null;
    }

    @Override
    public String searchUser(String name, UserInfo user) {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return "searchUser:" + name + ":" + runtimeMXBean.getName();
    }
}
