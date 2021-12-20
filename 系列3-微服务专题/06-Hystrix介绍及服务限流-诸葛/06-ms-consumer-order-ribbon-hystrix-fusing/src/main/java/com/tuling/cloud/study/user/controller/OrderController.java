package com.tuling.cloud.study.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tuling.cloud.study.user.entity.User;

@RestController
public class OrderController {
  private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
  @Autowired
  private RestTemplate restTemplate;
  

  @HystrixCommand(fallbackMethod = "findByIdFallback")
  @GetMapping("/user/{id}")
  public User findById(@PathVariable Long id) {
	logger.info("================请求用户中心接口，用户id:" + id + "==============");
    return restTemplate.getForObject("http://microservice-provider-user/" + id, User.class);
  }
  
  public User findByIdFallback(Long id) { 
    User user = new User();
    user.setId(-1L);
    user.setName("默认用户");
    return user;
  }

}
