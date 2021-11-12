package com.xiangxue.ch04.builder01.buildpattern;

import com.xiangxue.ch04.builder01.buildpattern.product.Man;
import com.xiangxue.ch04.builder01.buildpattern.product.Person;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：具体建造者
 */
public class ManBuilder extends PersonBuilder {
	
	private Person person;
	
	public ManBuilder() {
		this.person = new Man();
	}

	@Override
	public void buildHead() {
		person.setHead("Brave Head");
		
	}

	@Override
	public void buildBody() {
		person.setBody("Strong body");
		
	}

	@Override
	public void buildFoot() {
		person.setFoot("powful foot");
		
	}

	@Override
	public Person createPerson() {
		return person;
	}
	
	

}
