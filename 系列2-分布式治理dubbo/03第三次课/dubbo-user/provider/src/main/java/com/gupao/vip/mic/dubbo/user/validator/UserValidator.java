package com.gupao.vip.mic.dubbo.user.validator;

import com.gupao.vip.mic.dubbo.user.dto.UserLoginRequest;
import org.springframework.util.StringUtils;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class UserValidator {

    public static boolean checkUserLoginRequest(UserLoginRequest request){
        if(StringUtils.isEmpty(request.getName())||StringUtils.isEmpty(request.getPassword())){
            return false;
        }
        return true;
    }
}
