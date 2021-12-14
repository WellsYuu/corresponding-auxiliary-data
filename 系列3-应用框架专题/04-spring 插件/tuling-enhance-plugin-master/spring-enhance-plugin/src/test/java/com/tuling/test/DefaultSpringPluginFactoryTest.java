package com.tuling.test;


import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.aopalliance.aop.Advice;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.tuling.teach.spring.DefaultSpringPluginFactory;
import com.tuling.teach.spring.PluginConfig;
import com.tuling.teach.spring.PluginSite;
import com.tuling.teach.spring.SpringPluginFactory;
import com.tuling.test.service.UserService;


public class DefaultSpringPluginFactoryTest {
	private DefaultSpringPluginFactory factory;

	@Before
	public void init() {
		factory = new DefaultSpringPluginFactory();
	}

	// 构建通知测试
	@Test
	public void buildAdviceTest() throws Exception {
		PluginConfig config = new PluginConfig();
		config.setActive(true);
		config.setId("1");
		config.setJarRemoteUrl(
				"file:E:/workspace/tuling-teach-spring-plugin/target/tuling-teach-spring-plugin-0.0.1-SNAPSHOT.jar");
		config.setClassName("com.tuling.plugin.ServerLogPlugin");
		config.setName("服务参数日志打印");
		Advice advice = factory.buildAdvice(config);
		Assert.assertNotNull(advice);
	}

	// 本地安装测试
	@Test
	public void intallTest() {
		PluginConfig config = new PluginConfig();
		config.setActive(false);
		config.setId("2");
		config.setJarRemoteUrl(
				"file:D:/site/tuling-teach-spring-plugin-0.0.2-SNAPSHOT.jar");
		config.setClassName("com.tuling.plugin.CountingBeforeAdvice");
		config.setName("服务执行统计");
		factory.installPlugin(config, false);
		factory.getPluginList();
	}
	
	public void getPluginList(){
		factory.getPluginList();
	}

	// 启动时装载本地插件
	@Test
	public void loaderLocalTest() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-aop-test.xml");
		context.start();
		UserService service = context.getBean(UserService.class);
		service.addUser("hanmeimei");
	}

	// 动态激活插件测试
	@Test
	public void activeTest() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-aop-test.xml");
		context.start();
		SpringPluginFactory pluginFactory = context.getBean(SpringPluginFactory.class);
		UserService service = context.getBean(UserService.class);
		service.addUser("hanmeimei");
		pluginFactory.activePlugin("1");
		service.addUser("hanmeimei");
	}

	@Test
	public void disableTest() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-aop-test.xml");
		context.start();
		SpringPluginFactory pluginFactory = context.getBean(SpringPluginFactory.class);
		UserService service = context.getBean(UserService.class);
		service.addUser("hanmeimei");
		pluginFactory.disablePlugin("1");
		service.addUser("hanmeimei");
	}

	@Test
	public void buildSiteTest() {
		PluginSite site = new PluginSite();
		site.setName("我的插件小仓库");
		List<PluginConfig> configs = new ArrayList<>();
		{
			PluginConfig config = new PluginConfig();
			config.setActive(true);
			config.setId("1");
			config.setJarRemoteUrl(
					"file:D:/site/tuling-teach-spring-plugin-0.0.2-SNAPSHOT.jar");
			config.setClassName("com.tuling.plugin.ServerLogPlugin");
			config.setName("服务参数日志打印");
			configs.add(config);
		}
		{
			PluginConfig config = new PluginConfig();
			config.setActive(true);
			config.setId("2");
			config.setJarRemoteUrl(
					"file:D:/site/tuling-teach-spring-plugin-0.0.2-SNAPSHOT.jar");
			config.setClassName("com.tuling.plugin.CountingBeforeAdvice");
			config.setName("方法执行统计");
			configs.add(config);
		}
		site.setConfigs(configs);
		String json = JSON.toJSONString(site);
		System.out.println(json);
	}
	

}
