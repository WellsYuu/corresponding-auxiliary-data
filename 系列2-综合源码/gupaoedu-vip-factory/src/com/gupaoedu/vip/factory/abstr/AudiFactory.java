package com.gupaoedu.vip.factory.abstr;

import com.gupaoedu.vip.factory.Audi;
import com.gupaoedu.vip.factory.Car;


//具体的业务逻辑封装
public class AudiFactory extends AbstractFactory {

	@Override
	public Car getCar() {
		return new Audi();
	}

}
