package com.tuling.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerStart {

	public static void main(String[] args) throws Exception {
        SpringApplication.run(EurekaServerStart.class, args);
    }
}
