package com.enjoylearning.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.enjoylearning.mybatis.entity.TJobHistory;
import com.enjoylearning.mybatis.entity.TUser;

public interface TJobHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TJobHistory record);

    int insertSelective(TJobHistory record);

    TJobHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TJobHistory record);

    int updateByPrimaryKey(TJobHistory record);
    
    List<TJobHistory> selectByUserId(int userId);
    
    List<TUser> selectByEmailAndSex2(@Param("email")String email,@Param("sex")Byte sex);
}