package com.gupaoedu.crawler.model;

import java.io.Serializable;

public class House implements Serializable{
	private String name;	//楼盘名称
	private String cover;	//封面
	private String price;	//价格
	private String area;	//面积
	private String layout;	//布局类型
	private String type;	//用途：普通住宅、商住两用，写字楼，别墅
	private String address;	//坐落地址
	private String developers;//开发商
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDevelopers() {
		return developers;
	}
	public void setDevelopers(String developers) {
		this.developers = developers;
	}
	
	
}
