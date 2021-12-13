package com.gupaoedu.vip.factory.func;

import com.gupaoedu.vip.factory.Audi;
import com.gupaoedu.vip.factory.Car;

public class AudiFactory implements Factory {

	@Override
	public Car getCar() {
		return new Audi();
	}

}
