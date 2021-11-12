package com.enjoylearning.mybatis.session;

import java.util.List;

public interface SqlSession {
	
	  //根据传入的条件查询结果
	  <T> T selectOne(String statement, Object parameter);

	  //根据条件经过查询，返回泛型集合
	  <E> List<E> selectList(String statement, Object parameter);
	  
	  
	  //根据mapper接口获取接口对应的动态代理实现
	  <T> T getMapper(Class<T> type);


}
