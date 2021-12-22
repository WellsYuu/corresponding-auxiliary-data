package com.tuling.teach.service;

import java.util.Date;

/**
 * Created by Tommy on 2017/12/17.
 */
public class UserInfo implements java.io.Serializable {
	private String name;
	private String sex;
	private Integer status;
	private Date addTime;

	public UserInfo(String name, String sex, Integer status) {
		super();
		this.name = name;
		this.sex = sex;
		this.status = status;
	}

	public UserInfo() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

}
