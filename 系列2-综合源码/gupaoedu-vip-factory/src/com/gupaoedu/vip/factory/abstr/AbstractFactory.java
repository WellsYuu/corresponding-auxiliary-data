package com.gupaoedu.vip.factory.abstr;

import com.gupaoedu.vip.factory.Car;

public abstract class AbstractFactory {

 	protected abstract Car getCar();
 	
 	
 	//这段代码就是动态配置的功能
 	//固定模式的委派
 	public Car getCar(String name){
		if("BMW".equalsIgnoreCase(name)){
			return new BmwFactory().getCar();
		}else if("Benz".equalsIgnoreCase(name)){
			return new BenzFactory().getCar();
		}else if("Audi".equalsIgnoreCase(name)){
			return new AudiFactory().getCar();
		}else{
			System.out.println("这个产品产不出来");
			return null;
		}
	}

}
