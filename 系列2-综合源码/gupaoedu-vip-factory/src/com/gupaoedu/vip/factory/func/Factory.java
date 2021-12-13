package com.gupaoedu.vip.factory.func;

import com.gupaoedu.vip.factory.Car;

//工厂接口，就定义了所有工厂的执行标准
public interface Factory {

	//符合汽车上路标准
	//尾气排放标准
	//电子设备安全系数
	//必须配备安全带、安全气囊
	//轮胎的耐磨程度
	Car getCar();
	
}
