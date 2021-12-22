package com.tuling.springboot.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tuling.springboot.SpringbootStart;
import com.tuling.springboot.bean.User;
import com.tuling.springboot.mapper.UserMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootStart.class)
public class MybatisTest {
	@Autowired
	private UserMapper userMapper;
	@Test
	public void testInsert() throws Exception {
		int num = userMapper.insert("zhangsan222", 20,"长沙","13100000000");
		TestCase.assertEquals(num,1);
	}
	@Test
	public void testFindById() throws Exception {
		User u = userMapper.findById(6);
		TestCase.assertNotNull(u);
		System.out.println(u.getName());
	}
}