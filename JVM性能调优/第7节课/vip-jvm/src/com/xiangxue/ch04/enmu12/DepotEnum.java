package com.xiangxue.ch04.enmu12;

public enum DepotEnum {
	UNPAY(0,"未支付"),PAID(1,"已支付"),TIMOUT(-1,"超时");
	
	private int status;
	private String desc;
	private String dbInfo;//其他属性
	
	private DepotEnum(int status, String desc) {
		this.status = status;
		this.desc = desc;
	}

	public int getStatus() {
		return status;
	}

	public String getDesc() {
		return desc;
	}

	public String getDbInfo() {
		return dbInfo;
	}
	
	public int calcStatus(int params) {
		return status+params;
	}
	
	public static void main(String[] args) {
		for(DepotEnum e:DepotEnum.values()) {
			System.out.println(e+":"+e.calcStatus(14));
		}
	}
	
	

	
	
}
