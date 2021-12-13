package com.gupaoedu.jdbc.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.gupaoedu.jdbc.demo.dao.MemberDao;
import com.gupaoedu.jdbc.demo.entity.Member;

@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MemberDaoTest {
	
	@Autowired MemberDao memberDao;
	
	
	@Test
//	@Ignore
	public void testSelect(){
		System.out.println(JSON.toJSON(memberDao.selectByName("zhangsan")));
	}
	
	
	@Test
	@Ignore
	public void testInsert() throws Exception{
		Member entity = new Member();
		entity.setMname("zhangsan");
		memberDao.insert(entity);
	}
	
	@Test
	@Ignore
	public void testInsertAll() throws Exception{
		Member m1 = new Member();
		m1.setMname("m1");
		
		Member m2 = new Member();
		m2.setMname("m2");
		
		Member m3 = new Member();
		m3.setMname("m3");
		
		List<Member> entityList = new ArrayList<Member>();
		entityList.add(m1);
		entityList.add(m2);
		entityList.add(m3);
		
		memberDao.insertAll(entityList);
	}
	
}
