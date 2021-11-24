package com.gupao.vip.mic.dubbo.user;

import com.gupao.vip.mic.dubbo.user.dto.UserLoginRequest;
import com.gupao.vip.mic.dubbo.user.dto.UserLoginResponse;
import com.gupao.vip.mic.dubbo.user.dto.UserRegisterRequest;
import com.gupao.vip.mic.dubbo.user.dto.UserRegisterResponse;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public interface IUserCoreService {

    /**
     * 用户登录操作
     * @param userLoginRequest
     * @return
     */
    UserLoginResponse login(UserLoginRequest userLoginRequest);


    /*
     * 注册
     */
    UserRegisterResponse register(UserRegisterRequest userRegisterRequest);
}
