package com.gupaoedu.vip.factory.func;

import com.gupaoedu.vip.factory.Bmw;
import com.gupaoedu.vip.factory.Car;

public class BmwFactory implements Factory {

	@Override
	public Car getCar() {
		return new Bmw();
	}

}
