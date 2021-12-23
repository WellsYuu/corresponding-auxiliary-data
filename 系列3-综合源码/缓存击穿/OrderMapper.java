package com.edu.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edu.example.bean.Order;

public interface OrderMapper {
	public Order selectById(int id);
	public int deleteById(int id);
	public int insert(Order order);
	public int updateByPrimaryKeySelective(Order order);
	public List<Order> getAll();
	
	/**
	 * 根据主键id和指定状态改变订单现有状态
	 * @param id 主键id
	 * @param newStatus 新的状态
	 * @param status 旧状态
	 * @return
	 */
	public int updateStatusByPrimaryKeyAndStatus(@Param("id") Integer id,
			@Param("newStatus") Integer newStatus,@Param("status") Integer status);
	
	
}
