package com.edu.example.service.impl;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.edu.example.bean.User;
import com.edu.example.dao.UserMapper;
import com.edu.example.service.OrderService;
import com.edu.example.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	private static Logger logger = LogManager.getLogger(UserServiceImpl.class.getName());
	@Resource
	private UserMapper userMapper;
	@Resource
	private OrderService orderService;
	@Resource
	private TransactionTemplate transactionTemplate;
	
	@Override
	public User selectById(int id) {
		User user = userMapper.selectById(id);
		logger.info(user);
		return user;
	}
	
	@Transactional
	@Override
	public void deleteById(int id) {
		userMapper.deleteById(id);
	}
	
	@Transactional
	@Override
	public void insertUser(User user) {
		logger.info(user);
		userMapper.insert(user);
	}
}
