package com.enjoylearning.mybatis.mapper;

import com.enjoylearning.mybatis.entity.TUserRoleKey;

public interface TUserRoleMapper {
    int deleteByPrimaryKey(TUserRoleKey key);

    int insert(TUserRoleKey record);

    int insertSelective(TUserRoleKey record);
}