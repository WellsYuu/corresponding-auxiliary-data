package com.enjoy.cap7.bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;
@Component
public class Jeep {
	public Jeep(){
		System.out.println("Jeep.....constructor........");
	}
	@PostConstruct
	public void init(){
		System.out.println("Jeep.....@PostConstruct........");
	}
	
	@PreDestroy
	public void destory(){
		System.out.println("Jeep.....@PreDestroy......");
	}
}
