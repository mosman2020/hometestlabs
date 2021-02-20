package com.hometest.exceptionhandling.handler;

import com.google.common.base.Throwables;
import com.hometest.exceptionhandling.exception.BusinessException;
import com.hometest.exceptionhandling.exception.ValidationException;
import com.hometest.model.res.Error;
import com.hometest.model.res.ErrorData;
import com.hometest.service.MessageService;
import com.hometest.utils.ErrorCodes;
import org.apache.ibatis.session.SqlSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@ControllerAdvice

public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Autowired
	private MessageService messageService;

// Reviewed and is OK
	@SuppressWarnings("rawtypes")
	@ExceptionHandler()
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	public ResponseEntity<ErrorData> handleGeneralExceptions(Exception ex, HttpServletRequest httpRequest) {
		logger.error("General Exception : "+ex.getMessage());
		ex.printStackTrace();
		Throwable trhowable = Throwables.getRootCause(ex);
		ErrorData errorData = ErrorData.builder().code(ErrorCodes.INTERNAL_SYSTEM_ERROR).type(messageService.getMessage(ErrorCodes.INTERNAL_SYSTEM_ERROR)).message(trhowable.getMessage()).build();
		return ResponseEntity.badRequest().body(errorData);
	}

		
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorData> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		logger.error("JSON Parse Exception : "+ex.getMessage());
		ex.printStackTrace();
		 List<Error> errorMessages = ex.getBindingResult().getFieldErrors().stream()
				 	.map(err -> Error.builder().code(err.getCode()).message(err.getDefaultMessage()).source(err.getField()+":"+err.getRejectedValue()).build())
		            .distinct()
		            .collect(Collectors.toList());
		 
		ErrorData errorData = ErrorData.builder().code(ErrorCodes.VALIDATION_ERROR).message(messageService.getMessage(ErrorCodes.VALIDATION_ERROR)).Errors(errorMessages).build();
		return ResponseEntity.badRequest().body(errorData);
	}
	
	
	@SuppressWarnings("rawtypes")
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorData> handleValidationException(ValidationException ex, HttpServletRequest httpRequest) {
		
		logger.error("Validation Exception : "+ex.VALIDATION_ERROR);
		ex.printStackTrace();
		List<Error> errors = new ArrayList<Error>();
		Set<ConstraintViolation<Object>> constraintViolation= ex.getConstraintViolation();
		logger.info("---------------------Constraints-------------------------------");
		constraintViolation.forEach(
			    (constraint) -> {
			    	logger.info(constraint + ",");
			    	logger.info(constraint.getMessage());
			    	errors.add(Error.builder().code(constraint.getMessageTemplate()).message(constraint.getMessage()).source(""+constraint.getPropertyPath()).build());
			    }
			);
		ErrorData errorData = ErrorData.builder().code(ErrorCodes.VALIDATION_ERROR).message(messageService.getMessage(ErrorCodes.VALIDATION_ERROR)).Errors(errors).build();
		return ResponseEntity.badRequest().body(errorData);
		
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorData> handleJsonParseException(HttpMessageNotReadableException ex) {
		logger.error("JSON Parse Exception : "+ex.getMessage());
		ex.printStackTrace();
		List<Error> errors = new ArrayList<Error>();
		errors.add(Error.builder().code(ErrorCodes.INVALID_JSON_OBJECT).message(ex.getRootCause().getMessage()).build());
		ErrorData errorData = ErrorData.builder().code(ErrorCodes.GENERIC_FAILURE_ERROR).message(messageService.getMessage(ErrorCodes.GENERIC_FAILURE_ERROR)).Errors(errors).build();
		return ResponseEntity.badRequest().body(errorData);
	}
	
	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ResponseEntity<ErrorData> handleBusinessExceptions(BusinessException ex, HttpServletRequest httpRequest) {
		logger.error("Business Exception : "+ex.getCode());
		List<Error> errors = new ArrayList<Error>();
		errors.add(Error.builder().code(ex.getCode()).message(messageService.getMessage(ex.getCode())).build());
		ErrorData errorData = ErrorData.builder().code(ex.BUSINESS_ERROR).message(messageService.getMessage(ex.BUSINESS_ERROR)).Errors(errors).build();
		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorData);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorData> handleNoHandlerFoundException(NoHandlerFoundException ex) {
	    String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
	    logger.info(error);
		ex.printStackTrace();
	    List<Error> errors = new ArrayList<Error>();
		errors.add(Error.builder().code(ErrorCodes.NOT_FOUND).message(messageService.getMessage(ErrorCodes.NOT_FOUND)).source(ex.getRequestURL()).build());
		
		ErrorData errorData = ErrorData.builder().code(ErrorCodes.GENERIC_FAILURE_ERROR).Errors(errors).type(messageService.getMessage(ErrorCodes.GENERIC_FAILURE_ERROR)).message(ex.getMessage()).build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(new HttpHeaders()).body(errorData);
	}
	
	
	@ExceptionHandler()
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<ErrorData> handleAuthenticationException(AuthenticationException ex) {
		logger.error("AuthenticationException Exception : "+ex.getMessage());
		ex.printStackTrace();
		ErrorData errorData = ErrorData.builder().code(ErrorCodes.ACCESS_DENIED)
				.type(messageService.getMessage(ErrorCodes.ACCESS_DENIED))
				.message(messageService.getMessage(ex.getMessage())).build();
		return ResponseEntity.badRequest().body(errorData);
	}
	
	@ExceptionHandler()
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	public ResponseEntity<ErrorData> handleSqlSessionException(SqlSessionException ex) {
		logger.error("SqlSessionException Exception : "+ex.getMessage());
		ex.printStackTrace();
		ErrorData errorData = ErrorData.builder().code(ErrorCodes.INTERNAL_SYSTEM_ERROR).type(messageService.getMessage(ErrorCodes.INTERNAL_SYSTEM_ERROR)).message(ex.getMessage()).build();
		return ResponseEntity.badRequest().body(errorData);
	}
	
}
