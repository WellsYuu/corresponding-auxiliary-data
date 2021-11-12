package com.enjoy.cap9.service;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.enjoy.cap9.dao.TestDao;

@Service
public class TestService {
	//@Qualifier("testDao")
	//@Autowired(required=false)
	//Ч����һ����,��Autowiredһ������װ��bean
	//1,��֧��Primary����
	//2,��֧��Autowired false
	//@Resource(name="testDao2")
	@Inject
	private TestDao testDao;
	
	public void println(){
		System.out.println(testDao);
	}
}
