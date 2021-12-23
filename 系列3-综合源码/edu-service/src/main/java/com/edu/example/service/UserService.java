package com.edu.example.service;

import com.edu.example.bean.User;

public interface UserService {
	public User selectById(int id);
	public void deleteById(int id);
	public void insertUser(User user);
}
