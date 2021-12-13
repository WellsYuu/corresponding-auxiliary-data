package com.gupaoedu.vip.factory.abstr;

import com.gupaoedu.vip.factory.Bmw;
import com.gupaoedu.vip.factory.Car;

public class BmwFactory extends AbstractFactory {

	@Override
	public Car getCar() {
		return new Bmw();
	}

}
