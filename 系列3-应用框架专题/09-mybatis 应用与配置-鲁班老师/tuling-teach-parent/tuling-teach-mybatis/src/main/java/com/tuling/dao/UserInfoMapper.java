package com.tuling.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Tommy on 2017/10/22.
 */
public interface UserInfoMapper {
    public UserInfo getUser(int id);

    /**
     *  id
     *  password
     *   map
     *   object
     * @return
     */
    public List<UserInfo> selectUser(UserInfo user);

    public List<UserInfo> getUserByIds(int []ids);
}