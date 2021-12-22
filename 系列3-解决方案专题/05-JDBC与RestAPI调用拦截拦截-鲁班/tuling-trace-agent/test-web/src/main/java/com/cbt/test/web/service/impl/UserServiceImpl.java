package com.cbt.test.web.service.impl;

import com.cbt.test.web.bean.User;
import com.cbt.test.web.service.UserService;
import com.test.cbt.server.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Tommy on 2018/3/8.
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private GoodsService goodsService;

    @Override
    public User getUser(String userid, String name) {
        System.out.println("获取用户信息:" + userid);
        goodsService.getGoods(Long.parseLong(userid));
        User user = new User();
        user.setUserid(userid);
        user.setUserName(name);
        return user;
    }
}
