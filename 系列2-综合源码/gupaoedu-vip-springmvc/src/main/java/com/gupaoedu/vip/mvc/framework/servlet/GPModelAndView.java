package com.gupaoedu.vip.mvc.framework.servlet;

import java.util.Map;

public class GPModelAndView {

	//页面模板
	private String view;

	/** Model Map */
	//要往页面上带过去的值
	private Map<String,Object> model;
	
	public GPModelAndView(String view){
		this.view = view;
	}
	
	public GPModelAndView(String view,Map<String,Object> model){
		this.view = view;
		this.model = model;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public Map<String, Object> getModel() {
		return model;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}
	
	
}
