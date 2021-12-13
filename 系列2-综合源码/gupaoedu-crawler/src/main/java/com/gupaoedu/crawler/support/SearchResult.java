package com.gupaoedu.crawler.support;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索结果封装
 * 
 * @author Tom
 *
 */
public class SearchResult<T> {
	private Long total;//记录总数
	private Float useTime;//搜索花费时间(毫秒)
	private String keyword;//搜索关键字
	
	private List<T> data = new ArrayList<T>();//数据集合

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Float getUseTime() {
		return useTime;
	}

	public void setUseTime(Float useTime) {
		this.useTime = useTime;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
}
