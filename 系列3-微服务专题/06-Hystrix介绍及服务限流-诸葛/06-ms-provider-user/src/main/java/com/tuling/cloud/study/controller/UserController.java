package com.tuling.cloud.study.controller;

import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tuling.cloud.study.entity.User;
import com.tuling.cloud.study.repository.UserRepository;

@RestController
public class UserController {
	
  private final Logger logger = Logger.getLogger(getClass());
	
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private Registration registration;
  

  @GetMapping("/{id}")
  public User findById(@PathVariable Long id) throws Exception {
	  logger.info("用户中心接口：查询用户"+ id +"信息");
	  /*//测试超时触发降级
	  int sleepTime = new Random().nextInt(2000);
	  logger.info("sleepTime:" + sleepTime);
	  Thread.sleep(sleepTime);  */
	  
	  /*//测试熔断，传入不存在的用户id模拟异常情况
	  if (id == 10) {
	    throw new NullPointerException();
	  }*/
	  
	  //测试限流,线程资源隔离,模拟系统执行速度很慢的情况
	  Thread.sleep(3000);
	  
	  User findOne = userRepository.findOne(id);
	  return findOne;
  }
  
  @GetMapping("/getIpAndPort")
  public String findById() {
	  return registration.getHost() + ":" + registration.getPort();
  }
}
