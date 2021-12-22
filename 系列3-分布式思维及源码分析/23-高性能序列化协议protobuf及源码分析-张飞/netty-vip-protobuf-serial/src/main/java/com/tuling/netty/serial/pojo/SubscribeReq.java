package com.tuling.netty.serial.pojo;

import java.io.Serializable;

public class SubscribeReq implements Serializable {

	private static final long serialVersionUID = 1L;;
	
	private int subReqID;
	private String userName;
	private String productName;
	private String address;
	
	public int getSubReqID() {
		return subReqID;
	}
	public void setSubReqID(int subReqID) {
		this.subReqID = subReqID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "SubscribeReq [subReqID=" + subReqID + ", userName=" + userName + ", productName=" + productName
				+ ", address=" + address + "]";
	}
}
