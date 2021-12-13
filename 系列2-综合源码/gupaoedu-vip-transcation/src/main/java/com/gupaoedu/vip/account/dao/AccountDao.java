package com.gupaoedu.vip.account.dao;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao {

	private JdbcTemplate template;
	
	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource){
		template = new JdbcTemplate(dataSource);
	}
	
	
	public Double selectAccount(final String name){
		String sql = "select money from t_account where name = ?";
		Double money = template.queryForObject(sql,new Object[]{name},Double.class);
		return money;
	}
	
	
	/**
	 * 转出
	 * @param out 由哪个账号转出
	 * @param money 转出多少钱
	 * @throws Exception
	 */
	public int upateForOut(final String out,final Double money) throws Exception{
		String sql = "update t_account set money = money-? where name = ? and money >= ?";
		int count = template.update(sql,money,out,money);
		return count;
//		throw new Exception("系统延时，无法转账");
	}
	
	
	/**
	 * 转入
	 * @param in 转给谁
	 * @param money 转多少钱
	 * @throws Exception
	 */
	public int upateForIn(final String in,final Double money) throws Exception{
		String sql = "update t_account set money = money+? where name = ?";
		int count = template.update(sql,money,in);
		//throw new Exception("系统故障，资金撤回");
		return count;
	}
	
}
