package com.gupaoedu.vip.factory.abstr;

import com.gupaoedu.vip.factory.Benz;
import com.gupaoedu.vip.factory.Car;

public class BenzFactory extends AbstractFactory {

	@Override
	public Car getCar() {
		return new Benz();
	}

}
