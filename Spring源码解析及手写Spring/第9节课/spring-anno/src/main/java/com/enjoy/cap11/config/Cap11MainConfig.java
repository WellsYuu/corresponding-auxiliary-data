package com.enjoy.cap11.config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.aop.framework.autoproxy.InfrastructureAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/*
 * 
 * InfrastructureAdvisorAutoProxyCreator
 * 1,注册  2，利用后置处理器机制在创建以后，包装对象，返回一个代理对象（增强），代理对象执行方法时，利用拦截器键进行调用 
 * 
 *  AnnotationTransactionAttributeSource：事务增强器要用事务注解的信息，使用这个类解析事务注解
 *  TransactionInterceptor：保存了事务属性信息，事务管理器  MethodInterceptor
 *  当执行目标方法时：
 *     执行拦截器链
 *     事务拦截器：
 *     1.先获取事务相关属性
 *     2.获取PlatformTransactionManager事务管理器，直接到容器中获取platformTransactionManager bean实例
 *  执行目标方法：
 *  
 *     如果异常：completeTransactionAfterThrowing，利用事务管理回滚操作
 *     如果正常：利用 事务管理器，提交事务。
 *  事务管理器：
 *  
 */


@Configuration
@ComponentScan("com.enjoy.cap11")
@EnableTransactionManagement  //开启事务管理功能，对@Transactional起作用  EnableXXXX
public class Cap11MainConfig {
	//创建数据源
	@Bean
	public DataSource dataSource() throws PropertyVetoException{
		//这个c3p0封装了JDBC, dataSource接口的实现
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setUser("root");
		dataSource.setPassword("root");
		dataSource.setDriverClass("com.mysql.jdbc.Driver");
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
		return dataSource;
	}
	
	//jdbcTemplate能简化增删改查的操作
	@Bean
	public JdbcTemplate jdbcTemplate() throws PropertyVetoException{
		return new JdbcTemplate(dataSource());
	}
	//注册事务管理器
	@Bean
	public PlatformTransactionManager platformTransactionManager() throws PropertyVetoException{
		return new DataSourceTransactionManager(dataSource());
	}
}
