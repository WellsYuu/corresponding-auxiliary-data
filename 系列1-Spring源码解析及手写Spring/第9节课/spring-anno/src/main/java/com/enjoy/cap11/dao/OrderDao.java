package com.enjoy.cap11.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	//操作数据的方法
	public void insert(){
		String sql = "insert into `order` (ordertime, ordermoney, orderstatus) values(?,?,?)";
		jdbcTemplate.update(sql,new Date(),20,0);
	}
}
