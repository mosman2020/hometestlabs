package com.hometest.exceptionhandling.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  // lombok will generate no arguments constructor
@AllArgsConstructor // lombok will generate constructor with all parameters
public class BusinessException extends RuntimeException {
	
	private static final long serialVersionUID = -2423520208850150229L;
	
	private String code;
	private Object[] params ;

	public BusinessException(String code) {
		this.code = code;
	}
}
