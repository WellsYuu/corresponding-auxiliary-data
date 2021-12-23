package com.edu.example.service;

import com.edu.example.bean.Order;

public interface OrderService {
	public Order selectById(int id);
	public void deleteById(int id);
	public void insertOrder(Order order);
	public void pay(Order order);
}
