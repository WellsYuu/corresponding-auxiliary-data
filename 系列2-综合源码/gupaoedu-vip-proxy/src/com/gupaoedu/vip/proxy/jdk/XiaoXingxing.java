package com.gupaoedu.vip.proxy.jdk;

//小星星、单身
public class XiaoXingxing implements Person{

//	private String sex = "女";
//	private String name = "小星星";
	
	@Override
	public void findLove() {
//		System.out.println("我叫" + this.name + ",性别:" + this.sex + "我找对象的要求是:");
		System.out.println("高富帅");
		System.out.println("有房有车的");
		System.out.println("身高要求180cm以上，体重70kg");
	}

//	public String getSex() {
//		return sex;
//	}
//
//	public void setSex(String sex) {
//		this.sex = sex;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
	
	//回顾一下，满足代理模式应用场景的三个必要条件，穷取法
	//1、两个角色：执行者、被代理对象
	//2、注重过程，必须要做，被代理对象没时间做或者不想做（怕羞羞），不专业
	//3、执行者必须拿到被代理对象的个人资料（执行者持有被代理对象的引用）
	
	

}
