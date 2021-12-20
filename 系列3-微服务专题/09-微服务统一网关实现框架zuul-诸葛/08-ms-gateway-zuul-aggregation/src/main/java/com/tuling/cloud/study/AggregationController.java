package com.tuling.cloud.study;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class AggregationController {
  public static final Logger LOGGER = LoggerFactory.getLogger(ZuulApplication.class);

  @Autowired
  private RestTemplate restTemplate;
	
  @GetMapping("/aggregate/{id}")
  public Map<String, User> findById(@PathVariable Long id) throws Exception {
	  Map<String, User> dataMap = new HashMap<>();
	  User user = restTemplate.getForObject("http://microservice-provider-user/" + id, User.class);
	  User orderUser = restTemplate.getForObject("http://microservice-consumer-order/user/" + id, User.class);
	  dataMap.put("user", user);
	  dataMap.put("orderUser", orderUser);
	  return dataMap;
  }
}