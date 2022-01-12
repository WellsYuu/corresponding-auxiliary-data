package com.imooc;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.imooc.interfaces.ProxyCreator;
import com.imooc.proxys.JDKProxyCreator;

@SpringBootApplication
public class WebfluxclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxclientApplication.class, args);
	}

	/**
	 * 创建jdk代理工具类
	 * 
	 * @return
	 */
	@Bean
	ProxyCreator jdkProxyCreator() {
		return new JDKProxyCreator();
	}

	@Bean
	FactoryBean<IUserApi> userApi(ProxyCreator proxyCreator) {
		return new FactoryBean<IUserApi>() {

			@Override
			public Class<?> getObjectType() {
				return IUserApi.class;
			}

			/**
			 * 返回代理对象
			 */
			@Override
			public IUserApi getObject() throws Exception {
				return (IUserApi) proxyCreator
						.createProxy(this.getObjectType());
			}
		};
	}

}
