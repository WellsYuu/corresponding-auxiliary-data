package com.enjoy;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan(basePackages = "com.enjoy")
public class BootApp {
    public static void main(String[] args) {
        SpringApplication.run(BootApp.class, args);
    }
}
