package com.gupaoedu.vip.factory.func;

import com.gupaoedu.vip.factory.Benz;
import com.gupaoedu.vip.factory.Car;

public class BenzFactory implements Factory {

	@Override
	public Car getCar() {
		return new Benz();
	}

}
