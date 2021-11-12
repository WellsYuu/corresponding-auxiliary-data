package com.xiangxue.ch04.builder01.buildpattern;

import com.xiangxue.ch04.builder01.buildpattern.product.Person;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：导演者
 */
public class NvWa {
	
	public Person buildPerson(PersonBuilder pb) {
		pb.buildHead();
		pb.buildBody();
		pb.buildFoot();
		return pb.createPerson();
	}

}
