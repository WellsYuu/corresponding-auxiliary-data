package com.tuling.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderMicroStart {

	public static void main(String[] args) throws Exception {
        SpringApplication.run(OrderMicroStart.class, args);
    }
}
