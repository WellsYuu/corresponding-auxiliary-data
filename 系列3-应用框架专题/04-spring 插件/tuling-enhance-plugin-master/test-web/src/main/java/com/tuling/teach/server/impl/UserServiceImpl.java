package com.tuling.teach.server.impl;

import org.springframework.stereotype.Service;

import com.tuling.teach.server.UserService;

@Service
public class UserServiceImpl implements UserService {

	public String getName(String id) {
		return "name:hanmeimei id:" + id;
	}
}
