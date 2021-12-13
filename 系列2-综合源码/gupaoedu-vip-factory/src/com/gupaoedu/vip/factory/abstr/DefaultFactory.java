package com.gupaoedu.vip.factory.abstr;

import com.gupaoedu.vip.factory.Car;

public class DefaultFactory extends AbstractFactory {

	private AudiFactory defaultFactory = new AudiFactory();
	
	public Car getCar() {
		return defaultFactory.getCar();
	}

}
