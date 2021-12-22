package com.tuling.netty.day3.java.serial;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SubscribeReq implements Serializable {

	private static final long serialVersionUID = 1L;;
	
	private int subReqID;
	private String userName;
	private String productName;
	private List<String> addressList = new ArrayList<String>();
	
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
	public List<String> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<String> addressList) {
		this.addressList = addressList;
	}
	@Override
	public String toString() {
		return "SubscribeReq [subReqID=" + subReqID + ", userName=" + userName + ", productName=" + productName
				+ ", addressList=" + addressList + "]";
	}
}
