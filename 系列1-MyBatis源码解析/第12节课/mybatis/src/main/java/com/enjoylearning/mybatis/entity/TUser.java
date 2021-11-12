package com.enjoylearning.mybatis.entity;

import java.io.Serializable;

public class TUser implements Serializable{
	
    private Integer id;

    private String userName;

    private String realName;

    private String mobile;

    private String email;

    private String note;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "TUser [id=" + id + ", userName=" + userName + ", realName="
				+ realName + ", mobile=" + mobile + ", email="
				+ email + ", note=" + note + "]";
	}


    
}