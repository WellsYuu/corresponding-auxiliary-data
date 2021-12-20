package com.tuling.cloud.study.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tuling.cloud.study.user.entity.User;

@RestController
public class OrderController {
  private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private LoadBalancerClient loadBalancerClient;

  @GetMapping("/user/{id}")
  public User findById(@PathVariable Long id) {
    return this.restTemplate.getForObject("http://microservice-provider-user/" + id, User.class);
  }
  
  @GetMapping("/user/getIpAndPort")
  public String getIpAndPort() {
	  return this.restTemplate.getForObject("http://microservice-provider-user/getIpAndPort", String.class);
  }
  
  @GetMapping("/user1/getIpAndPort")
  public String getUserIpAndPort() {
	  return this.restTemplate.getForObject("http://microservice-provider-user222/getIpAndPort", String.class);
  }

  @GetMapping("/log-user-instance")
  public void logUserInstance() {
    ServiceInstance serviceInstance = this.loadBalancerClient.choose("microservice-provider-user");
    // 打印当前选择的是哪个节点
    OrderController.LOGGER.info("{}:{}:{}", serviceInstance.getServiceId(), serviceInstance.getHost(), serviceInstance.getPort());
  }
}
