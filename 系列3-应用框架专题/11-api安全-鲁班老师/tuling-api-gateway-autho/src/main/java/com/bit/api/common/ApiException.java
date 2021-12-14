package com.bit.api.common;

public class ApiException extends Exception{
	private String code;

	public ApiException(String message) {
		super(message);
	}

	public ApiException( String code,String message) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
