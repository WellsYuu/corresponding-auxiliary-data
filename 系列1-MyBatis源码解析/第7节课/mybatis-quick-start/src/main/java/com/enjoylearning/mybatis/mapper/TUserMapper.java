package com.enjoylearning.mybatis.mapper;

import com.enjoylearning.mybatis.entity.TUser;

public interface TUserMapper {
	
    TUser selectByPrimaryKey(Integer id);
    
}