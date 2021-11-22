package com.gupao.vip.mic.dubbo.user;

import com.gupao.vip.mic.dubbo.dal.UserDao;
import com.gupao.vip.mic.dubbo.user.dto.DebitRequest;
import com.gupao.vip.mic.dubbo.user.dto.DebitResponse;
import com.gupao.vip.mic.dubbo.user.dto.UserLoginRequest;
import com.gupao.vip.mic.dubbo.user.dto.UserLoginResponse;
import com.gupao.vip.mic.dubbo.user.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Service("userService")
public class UserServiceImpl implements IUserService{

    @Autowired
    UserDao userDao;

    public UserLoginResponse login(UserLoginRequest request) {
        //参数校验
        UserLoginResponse response=new UserLoginResponse();

        response.setCode("100002");
        response.setMemo("登录失败,帐号或密码错误");
        return response;
    }

    public DebitResponse debit(DebitRequest request) {
        DebitResponse response=new DebitResponse();
        userDao.updateUser();
        response.setCode("000000");
        response.setMemo("成功");
        return response;
    }
}
