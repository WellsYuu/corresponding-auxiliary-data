package com.enjoy.king.tomcat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* 引入SpringBoot功能
* @author 【享学课堂】 King老师    架构技术QQ群：684504192
* @throws Exception
*/
@SpringBootApplication
public class TomcatApplication{
    public static void main(String[] args) {
    	//启动Springboot容器,即启动Tomcat服务
        SpringApplication.run(TomcatApplication.class, args);
    }
}