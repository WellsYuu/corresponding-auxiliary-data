package com.enjoylearning.mybatis;

import java.util.List;

import com.enjoylearning.mybatis.entity.TUser;
import com.enjoylearning.mybatis.mapper.TUserMapper;
import com.enjoylearning.mybatis.session.SqlSession;
import com.enjoylearning.mybatis.session.SqlSessionFactory;

public class TestMybatis {
	
	public static void main(String[] args) {
		//1.实例化SqlSessionFactory，加载数据库配置文件以及mapper.xml文件到configuration对象
		SqlSessionFactory factory = new SqlSessionFactory();
		//2.获取sqlsession对象
		SqlSession session = factory.openSession();
		//3.通过动态代理跨越面向接口编程和ibatis编程模型的鸿沟
		TUserMapper userMapper = session.getMapper(TUserMapper.class);
		//4.遵循jdbc规范，通过底层的四大对象的合作完成数据查询和数据转化
		TUser user = userMapper.selectByPrimaryKey(1);
		System.out.println(user);
		
		
		List<TUser> users = userMapper.selectAll();
		for (TUser tUser : users) {
			
			System.out.println(tUser);
		}
		
		
		
	}

}
