/**
 * 
 */
package com.hometest.exceptionhandling.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.hometest.utils.ErrorCodes;

import lombok.Data;

/**
 * @author mosman
 * @Date Sep 23, 2020
 * @ValidationException.java
 * @Pacom.hometest.exceptionhandling.exception @5:35:47 PM
 */
@Data
public class ValidationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public static String VALIDATION_ERROR = ErrorCodes.VALIDATION_ERROR;
	
	@SuppressWarnings("rawtypes")
	private Set<ConstraintViolation<Object>> constraintViolation;

	public ValidationException(@SuppressWarnings("rawtypes") Set<ConstraintViolation<Object>> constraintViolation) {
		this.constraintViolation = constraintViolation;
	}

}
