package com.enjoylearning.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.enjoylearning.mybatis.entity.THealthReportFemale;
import com.enjoylearning.mybatis.entity.TUser;
import com.enjoylearning.mybatis.mapper.THealthReportFemaleMapper;
import com.enjoylearning.mybatis.mapper.TUserMapper;

public class AssociationQueryTest {
	
	private SqlSessionFactory sqlSessionFactory;

	@Before
	public void init() throws IOException {
		
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// 1.读取mybatis配置文件创SqlSessionFactory
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		inputStream.close();
	}
	
	
	
	@Test
	// 1对1两种关联方式
	public void testOneToOne() {
		// 2.获取sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.获取对应mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		// 4.执行查询语句并返回结果
		// ----------------------
//		List<TUser> list1 = mapper.selectUserPosition1();
//		for (TUser tUser : list1) {
//			System.out.println(tUser);
//		}
		
		List<TUser> list2 = mapper.selectUserPosition2();
		for (TUser tUser : list2) {
			System.out.println(tUser.getPosition());
		}
	}

	
	
	
	@Test
	// 1对多两种关联方式
	public void testOneToMany() {
		// 2.获取sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.获取对应mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		// 4.执行查询语句并返回结果
		// ----------------------
		List<TUser> selectUserJobs1 = mapper.selectUserJobs1();
		List<TUser> selectUserJobs2 = mapper.selectUserJobs2();
		for (TUser tUser : selectUserJobs1) {
			System.out.println(tUser);
		}
		for (TUser tUser : selectUserJobs2) {
			System.out.println(tUser.getJobs().size());
		}
	}
	
	@Test
	public void testDiscriminator(){
		// 2.获取sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.获取对应mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
//		// 4.执行查询语句并返回结果
//		// ----------------------
		List<TUser> list = mapper.selectUserHealthReport();
		for (TUser tUser : list) {
			System.out.println(tUser);
		}
	}
	
	
	@Test
	// 多对多
	public void testManyToMany() {
		// 2.获取sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.获取对应mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		// 4.执行查询语句并返回结果
		// ----------------------
		List<TUser> list = mapper.selectUserRole();
		for (TUser tUser : list) {
			System.out.println(tUser.getRoles().size());
		}
	}
}
