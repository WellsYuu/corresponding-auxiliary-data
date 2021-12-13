package com.gupaoedu.jdbc.demo.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.gupaoedu.jdbc.demo.entity.Member;
import com.gupaoedu.jdbc.framework.BaseDaoSupport;
import com.gupaoedu.jdbc.framework.QueryRule;

@Repository
public class MemberDao extends BaseDaoSupport<Member, Long> {

	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		this.setDataSourceReadOnly(dataSource);
		this.setDataSourceWrite(dataSource);
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public List<Member> selectByName(String name){
		//构建一个QureyRule 查询规则
		QueryRule queryRule = QueryRule.getInstance();
		//查询一个name= 赋值 结果，List
		queryRule.andEqual("name", name);
//		queryRule.
		//相当于自己再拼SQL语句
		return super.select(queryRule);
	}
	
	/**
	 * 
	 */
	public int insert(Member entity) throws Exception{
		return super.insert(entity);
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	public int insertAll(List<Member> entityList) throws Exception{
		return super.insertAll(entityList);
	}
	
	
}
