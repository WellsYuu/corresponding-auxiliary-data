package com.jiagouedu.services.common;import com.jiagouedu.core.dao.QueryModel;import java.io.Serializable;public class Advert extends QueryModel implements Serializable {	private static final long serialVersionUID = 1L;	private String id;	private String title;	private String code;	private String remark;	private String html;	private String startdate;	private String enddate;	private String status;//y:启用,n:禁用 ；默认启用	private String useImagesRandom;//使用随机选择图集		/**	 * 广告启用、不启用状态	 */	public static final String advert_status_y = "y";//启用	public static final String advert_status_n = "n";//不启用		/**	 * 广告是否使用随即图集	 */	public static final String advert_useImagesRandom_y = "y";//使用	public static final String advert_useImagesRandom_n = "n";//不使用	public void clear() {		super.clear();		id = null;		title = null;		code = null;		remark = null;		html = null;		startdate = null;		enddate = null;		status = null;		useImagesRandom = null;	}	public String getId() {		return id;	}	public void setId(String id) {		this.id = id;	}	public String getTitle() {		return title;	}	public void setTitle(String title) {		this.title = title;	}	public String getCode() {		return code;	}	public void setCode(String code) {		this.code = code;	}	public String getRemark() {		return remark;	}	public void setRemark(String remark) {		this.remark = remark;	}	public String getHtml() {		return html;	}	public void setHtml(String html) {		this.html = html;	}	public String getStartdate() {		return startdate;	}	public void setStartdate(String startdate) {		this.startdate = startdate;	}	public String getEnddate() {		return enddate;	}	public void setEnddate(String enddate) {		this.enddate = enddate;	}	public String getStatus() {		return status;	}	public void setStatus(String status) {		this.status = status;	}	public String getUseImagesRandom() {		return useImagesRandom;	}	public void setUseImagesRandom(String useImagesRandom) {		this.useImagesRandom = useImagesRandom;	}}