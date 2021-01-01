package com.hometest.exceptionhandling.exception;

import com.hometest.utils.ErrorCodes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  // lombok will generate no arguments constructor
@AllArgsConstructor // lombok will generate constructor with all parameters
public class BusinessException extends RuntimeException {
	
	private static final long serialVersionUID = -2423520208850150229L;
	
	public static String BUSINESS_ERROR = ErrorCodes.BUSINESS_ERROR;
	
	private String code ;
	private Object[] params ;

	public BusinessException(String code) {
		this.code = code;
	}
	
}
