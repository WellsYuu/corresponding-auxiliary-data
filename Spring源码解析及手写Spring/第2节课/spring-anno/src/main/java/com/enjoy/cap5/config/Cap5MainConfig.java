package com.enjoy.cap5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.enjoy.cap1.Person;
@Configuration
public class Cap5MainConfig {
	@Bean("person")
	public Person person(){
		System.out.println("�����������person.......");
		return new Person("person",20);
	}
	
	@Conditional(WinCondition.class)
	@Bean("lison")
	public Person lison(){
		System.out.println("�����������lison.......");
		return new Person("Lison",58);
	}
	@Conditional(LinCondition.class)
	@Bean("james")//bean�������е�IDΪjames, IOC����MAP,  map.put("id",value)
	public Person james(){
		System.out.println("�����������james.......");
		return new Person("james",20);
	}
	
}
