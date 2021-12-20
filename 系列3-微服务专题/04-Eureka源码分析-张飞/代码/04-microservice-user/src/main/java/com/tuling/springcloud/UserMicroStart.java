package com.tuling.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableEurekaClient
public class UserMicroStart {

	public static void main(String[] args) throws Exception {
        SpringApplication.run(UserMicroStart.class, args);
    }
}
