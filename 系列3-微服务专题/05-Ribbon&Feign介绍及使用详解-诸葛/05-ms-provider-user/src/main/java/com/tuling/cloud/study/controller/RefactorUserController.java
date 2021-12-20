package com.tuling.cloud.study.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tuling.cloud.study.entity.User;
import com.tuling.cloud.study.repository.UserRepository;
import com.tuling.cloud.study.service.UserService;

@RestController
public class RefactorUserController implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
    public User getUser(@PathVariable Long id) {
		User findOne = userRepository.findOne(id);
	    return findOne;
    }

}