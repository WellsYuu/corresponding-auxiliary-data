package com.tuling.test.service;

public class UserServiceImpl implements UserService {
	private UserDao userDao;

	public UserServiceImpl() {
		System.out.println("创建Bean");
	}

	public String getUser() {
		System.out.println("执行用户获取方法");
		return "hanmeimei";
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public String setUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addUser(String name) {
		System.out.println("添加用户:" + name);
	}
}
