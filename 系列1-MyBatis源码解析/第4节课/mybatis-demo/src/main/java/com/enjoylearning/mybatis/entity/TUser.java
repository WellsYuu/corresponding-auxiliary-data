package com.enjoylearning.mybatis.entity;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public class TUser implements Serializable{
    private Integer id;

    private String userName;

    private String realName;

    private Byte sex;

    private String mobile;

    private String email;

    private String note;

    private TPosition position;
    
    private List<TJobHistory> jobs ;
    
    private List<HealthReport> healthReports;

    
    private List<TRole> roles;
/*    
	public TUser(Integer id, String userName) {
		super();
		this.id = id;
		this.userName = userName;
	}
	*/
    

    
    


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

    public Byte getSex() {
        return sex;
    }

    public TPosition getPosition() {
		return position;
	}

	public void setPosition(TPosition position) {
		this.position = position;
	}

	public void setSex(Byte sex) {
        this.sex = sex;
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

   

	public List<TJobHistory> getJobs() {
		return jobs;
	}

	public void setJobs(List<TJobHistory> jobs) {
		this.jobs = jobs;
	}
	
	

	public List<HealthReport> getHealthReports() {
		return healthReports;
	}

	public void setHealthReports(List<HealthReport> healthReports) {
		this.healthReports = healthReports;
	}
	
	

	public List<TRole> getRoles() {
		return roles;
	}

	public void setRoles(List<TRole> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		String positionId=  (position == null ? "" : String.valueOf(position.getId()));
		return "TUser [id=" + id + ", userName=" + userName + ", realName="
				+ realName + ", sex=" + sex + ", mobile=" + mobile + ", email="
				+ email + ", note=" + note + ", positionId=" + positionId + "]";
	}
	
	public static void main(String[] args) {
		System.out.println(1<<2);
	}

	
	
    
}