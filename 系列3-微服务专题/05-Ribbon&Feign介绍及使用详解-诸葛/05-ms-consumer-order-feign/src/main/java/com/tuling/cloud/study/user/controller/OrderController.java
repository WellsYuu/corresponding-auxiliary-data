package com.tuling.cloud.study.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tuling.cloud.study.user.entity.User;
import com.tuling.cloud.study.user.feign.RefactorUserService;
import com.tuling.cloud.study.user.feign.UserFeignClient;

@RestController
public class OrderController {
  @Autowired
  private UserFeignClient userFeignClient;
  @Autowired
  private RefactorUserService refactorUserService;

  @GetMapping("/user/{id}")
  public User findById(@PathVariable Long id) {
    return userFeignClient.findById(id);
  }
  
  @GetMapping("/user-extends/{id}")
  public com.tuling.cloud.study.entity.User findById2(@PathVariable Long id) {
	  return refactorUserService.getUser(id);
  }
  
}
