package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.sql.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 房屋管理表
 * </p>
 *
 * @author jiangzh
 * @since 2017-12-30
 */
@TableName("tbl_house")
public class TblHouse extends Model<TblHouse> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	@TableField("house_user")
	private String houseUser;
	@TableField("house_address")
	private String houseAddress;
	@TableField("house_time")
	private Date houseTime;
	@TableField("house_desc")
	private String houseDesc;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHouseUser() {
		return houseUser;
	}

	public void setHouseUser(String houseUser) {
		this.houseUser = houseUser;
	}

	public String getHouseAddress() {
		return houseAddress;
	}

	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}

	public Date getHouseTime() {
		return houseTime;
	}

	public void setHouseTime(Date houseTime) {
		this.houseTime = houseTime;
	}

	public String getHouseDesc() {
		return houseDesc;
	}

	public void setHouseDesc(String houseDesc) {
		this.houseDesc = houseDesc;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TblHouse{" +
			"id=" + id +
			", houseUser=" + houseUser +
			", houseAddress=" + houseAddress +
			", houseTime=" + houseTime +
			", houseDesc=" + houseDesc +
			"}";
	}
}
