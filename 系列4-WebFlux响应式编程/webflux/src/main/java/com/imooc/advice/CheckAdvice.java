package com.imooc.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.imooc.exceptions.CheckException;

/**
 * 异常处理切面
 * 
 * @author 晓风轻
 *
 */
@ControllerAdvice
public class CheckAdvice {

	@ExceptionHandler(WebExchangeBindException.class)
	public ResponseEntity<String> handleBindException(
			WebExchangeBindException e) {
		return new ResponseEntity<String>(toStr(e), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CheckException.class)
	public ResponseEntity<String> handleCheckException(
			CheckException e) {
		return new ResponseEntity<String>(toStr(e), HttpStatus.BAD_REQUEST);
	}

	private String toStr(CheckException e) {
		return e.getFieldName() + ": 错误的值 " + e.getFieldValue();
	}

	/**
	 * 把校验异常转换为字符串
	 * 
	 * @param ex
	 * @return
	 */
	private String toStr(WebExchangeBindException ex) {
		return ex.getFieldErrors().stream()
				.map(e -> e.getField() + ":" + e.getDefaultMessage())
				.reduce("", (s1, s2) -> s1 + "\n" + s2);
	}

}
