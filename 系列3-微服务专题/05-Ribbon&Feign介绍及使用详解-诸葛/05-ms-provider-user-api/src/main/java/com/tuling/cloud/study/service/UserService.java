package com.tuling.cloud.study.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tuling.cloud.study.entity.User;

public interface UserService {

  @GetMapping("/user/{id}")
  public User getUser(@PathVariable(value = "id") Long id);
  
}
