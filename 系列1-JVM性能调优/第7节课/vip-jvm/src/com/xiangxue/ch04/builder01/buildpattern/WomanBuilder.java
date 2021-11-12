package com.xiangxue.ch04.builder01.buildpattern;

import com.xiangxue.ch04.builder01.buildpattern.product.Person;
import com.xiangxue.ch04.builder01.buildpattern.product.Woman;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：具体建造者
 */
public class WomanBuilder extends PersonBuilder {
	
	private Person person;
	
	public WomanBuilder() {
		this.person = new Woman();
	}

	@Override
	public void buildHead() {
		person.setHead("Pretty Head");
		
	}

	@Override
	public void buildBody() {
		person.setBody("soft body");
		
	}

	@Override
	public void buildFoot() {
		person.setFoot("long white foot");
		
	}

	@Override
	public Person createPerson() {
		return person;
	}
	
	

}
