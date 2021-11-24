package com.gupao.vip.mic.dubbo.services;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.gupao.vip.mic.dubbo.dal.entity.User;
import com.gupao.vip.mic.dubbo.dal.persistence.UserMapper;
import com.gupao.vip.mic.dubbo.exception.ExceptionUtil;
import com.gupao.vip.mic.dubbo.exception.ServiceException;
import com.gupao.vip.mic.dubbo.exception.ValidateException;
import com.gupao.vip.mic.dubbo.user.IUserCoreService;
import com.gupao.vip.mic.dubbo.user.constants.Constants;
import com.gupao.vip.mic.dubbo.user.constants.ResponseCodeEnum;
import com.gupao.vip.mic.dubbo.user.dto.UserLoginRequest;
import com.gupao.vip.mic.dubbo.user.dto.UserLoginResponse;
import com.gupao.vip.mic.dubbo.user.dto.UserRegisterRequest;
import com.gupao.vip.mic.dubbo.user.dto.UserRegisterResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Service("userLoginService")
public class UserCoreService implements IUserCoreService {

    Logger logger= LoggerFactory.getLogger(UserCoreService.class);

    @Autowired
    UserMapper userMapper;

    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        logger.info("begin UserCoreService.login,request:【"+userLoginRequest+"】");
        UserLoginResponse response=new UserLoginResponse();
        try{
            beforeValidate(userLoginRequest);

            User user=userMapper.getUserByUserName(userLoginRequest.getUsername());
            if(user==null||!user.getPassword().equals(userLoginRequest.getPassword())){
                response.setCode(ResponseCodeEnum.USER_OR_PASSWORD_ERROR.getCode());
                response.setMsg(ResponseCodeEnum.USER_OR_PASSWORD_ERROR.getMsg());
                return response;
            }
            //todo 判断用户状态
            response.setAvatar(user.getAvatar());
            response.setMobile(user.getMobile());
            response.setRealName(user.getRealname());
            response.setSex(user.getSex());

            response.setCode(ResponseCodeEnum.SYS_SUCCESS.getCode());
            response.setMsg(ResponseCodeEnum.SYS_SUCCESS.getMsg());
            return response;
        }catch(Exception e){
            ServiceException serviceException=(ServiceException) ExceptionUtil.handlerException4biz(e);
            response.setCode(serviceException.getErrorCode());
            response.setMsg(serviceException.getErrorMessage());
        }finally {
            logger.info("login response:【"+response+"】");
        }
        return response;
    }

    public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
        logger.info("begin UserCoreService.register,request:【"+userRegisterRequest+"】");

        UserRegisterResponse response=new UserRegisterResponse();
        try{
            beforeRegisterValidate(userRegisterRequest);

            User user=new User();
            user.setUsername(userRegisterRequest.getUsername());
            user.setPassword(userRegisterRequest.getPassword());
            user.setStatus(Constants.FORZEN_USER_STATUS);
            user.setCreateTime(new Date());

            int effectRow=userMapper.insertSelective(user);
            if(effectRow>0){
                response.setCode(ResponseCodeEnum.SYS_SUCCESS.getCode());
                response.setMsg(ResponseCodeEnum.SYS_SUCCESS.getMsg());
                return  response;
            }
            response.setCode(ResponseCodeEnum.DATA_SAVE_ERROR.getCode());
            response.setMsg(ResponseCodeEnum.DATA_SAVE_ERROR.getMsg());
            return  response;
        }catch (DuplicateKeyException e){
            //TODO 用户名重复
        }catch(Exception e){
            ServiceException serviceException=(ServiceException) ExceptionUtil.handlerException4biz(e);
            response.setCode(serviceException.getErrorCode());
            response.setMsg(serviceException.getErrorMessage());
        }finally {
            logger.info("register response:【"+response+"】");
        }

        return response;
    }

    private void beforeRegisterValidate(UserRegisterRequest request){
        if(null==request){
            throw new ValidateException("请求对象为空");
        }
        if(StringUtils.isEmpty(request.getUsername())){
            throw new ValidateException("用户名为空");
        }
        if(StringUtils.isEmpty(request.getPassword())){
            throw new ValidateException("密码为空");
        }
        if(StringUtils.isEmpty(request.getMobile())){
            throw new ValidateException("密码为空");
        }
    }

    private void beforeValidate(UserLoginRequest request){
        if(null==request){
            throw new ValidateException("请求对象为空");
        }
        if(StringUtils.isEmpty(request.getUsername())){
            throw new ValidateException("用户名为空");
        }
        if(StringUtils.isEmpty(request.getPassword())){
            throw new ValidateException("密码为空");
        }
    }


}
