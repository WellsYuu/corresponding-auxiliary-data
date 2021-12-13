package com.gupaoedu.jdbc.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_member")
public class Member implements Serializable{
	@Id
//	@Transient
	private Long id;
	
	
	@Column(name="name")
	private String mname;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String name) {
		this.mname = name;
	}
	
}
