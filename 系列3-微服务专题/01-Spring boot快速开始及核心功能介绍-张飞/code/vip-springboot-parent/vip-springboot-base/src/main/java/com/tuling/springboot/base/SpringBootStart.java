package com.tuling.springboot.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableAutoConfiguration
//@ComponentScan(basePackages={"com.tuling.springboot"})
@SpringBootApplication
public class SpringBootStart {

	public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBootStart.class, args);
    }
}
