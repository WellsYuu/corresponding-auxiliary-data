package com.gupaoedu.vip.factory.abstr;

import com.gupaoedu.vip.factory.Audi;
import com.gupaoedu.vip.factory.Car;


//�����ҵ���߼���װ
public class AudiFactory extends AbstractFactory {

	@Override
	public Car getCar() {
		return new Audi();
	}

}
