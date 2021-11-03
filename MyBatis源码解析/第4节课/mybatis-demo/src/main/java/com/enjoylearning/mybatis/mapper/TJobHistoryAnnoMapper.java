package com.enjoylearning.mybatis.mapper;

import java.util.List;

import javax.annotation.Generated;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.enjoylearning.mybatis.entity.TJobHistory;

public interface TJobHistoryAnnoMapper {
	
    int deleteByPrimaryKey(Integer id);


    int insertSelective(TJobHistory record);

    TJobHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TJobHistory record);

    int updateByPrimaryKey(TJobHistory record);
    
    
    @Results(id="jobInfo",value={
    		@Result(property="id",column="id",id = true),
    		@Result(property="userId",column="user_id"),
    		@Result(property="compName",column="comp_name"),
    		@Result(property="years",column="years"),
    		@Result(property="title",column="title")
    })
    @Select("select id, user_id, comp_name, years, title from t_job_history"
    		+ "	where user_id = #{userId}")
    List<TJobHistory> selectByUserId(int userId);
    
    @ResultMap("jobInfo")
    @Select("select id, user_id, comp_name, years, title from t_job_history")
    List<TJobHistory> selectAll();
    
    @Insert("insert into t_job_history (id, user_id, comp_name,	years, title)"
    		+ "	values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER},"
    		+ "#{compName,jdbcType=VARCHAR},"
    		+ "#{years,jdbcType=INTEGER}, #{title,jdbcType=VARCHAR})")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(TJobHistory record);
}