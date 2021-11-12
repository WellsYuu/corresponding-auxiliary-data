package com.enjoylearning.mybatis.mapper;

import java.util.List;

import com.enjoylearning.mybatis.entity.TUser;

public interface TUserMapper {
	


    TUser selectByPrimaryKey(Integer id);
    
    List<TUser> selectAll();

    
    
    
    
    
}