package com.bit.api.service;

import com.bit.api.core.ApiRequest;
import com.sun.deploy.net.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bit.api.core.APIMapping;

// DEMO
@Service
public class UserServiceImpl {
    // 无缝集成
    @APIMapping(value = "bit.api.user.getUser",useLogin = true)
    public UserInfo getUser(Long userId) {
        Assert.notNull(userId);
        UserInfo info = new UserInfo();
        info.setName("小明");
        info.setSex("男");
        info.setUserId(userId);
        info.setIdcard("430527198108145443");
        if (info.getSex().equals("男0")) {
            // throw new Exception()
        }
        return info;
    }

    /**
     * 获取用户所有的订单信息
     * @param beforeTime
     */
    @APIMapping(value = "bit.api.user.getUser")
    public void getOrders(Long beforeTime, ApiRequest request){

    }

    @APIMapping("bit.api.user.getUser2")
    public UserInfo getUser4(Long userId) {
        Assert.notNull(userId);
        UserInfo info = new UserInfo();
        info.setName("小明");
        info.setSex("男");
        info.setUserId(userId);
        info.setIdcard("430527198108145443");
        if (info.getSex().equals("男0")) {
            // throw new Exception()
        }

        return info;
    }

    @APIMapping("bit.api.user.getUser2")
    public UserInfo getUser2(Long userId) {
        Assert.notNull(userId);

        UserInfo info = new UserInfo();
        info.setName("小明2");
        info.setSex("男");
        info.setUserId(userId);
        info.setIdcard("430527198108145443");
        return info;
    }
}
