package com.enjoylearning.mybatis.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.enjoylearning.mybatis.entity.TUser;
import com.enjoylearning.mybatis.mapper.TUserMapper;

@Service
public class UserServiceImpl implements UserService {
	
	@Resource(name="tUserMapper")
	private TUserMapper userMapper;

	@Override
	public TUser getUserById(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

}
