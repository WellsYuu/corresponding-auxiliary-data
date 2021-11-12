package com.xiangxue.ch04.builder01.buildpattern;

import com.xiangxue.ch04.builder01.buildpattern.product.Person;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：抽象建造者
 */
public abstract class PersonBuilder {
	
	//建造部件
	public abstract void buildHead();
	public abstract void buildBody();
	public abstract void buildFoot();
	
	public abstract Person createPerson();

}
