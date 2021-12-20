package com.tuling.cloud.study.user.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.tuling.cloud.study.service.UserService;

@FeignClient(name = "microservice-provider-user")
public interface RefactorUserService extends UserService {

}