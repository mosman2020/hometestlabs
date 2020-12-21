package com.hometest.exceptionhandling.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hometest.enums.Language;
import com.hometest.exceptionhandling.exception.BusinessException;
import com.hometest.exceptionhandling.exception.ValidationException;
import com.hometest.interceptor.CustomHttpRequestBody;
import com.hometest.service.MessageService;
import com.hometest.utils.ErrorCodes;
import com.hometest.utils.JsonUtils;
import com.hometest.utils.payload.request.Request;
import com.hometest.utils.payload.request.RequestHeader;
import com.hometest.utils.payload.response.Response;
import com.hometest.utils.payload.response.ResponseHeader;

@RestController
@ControllerAdvice

public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Autowired
	private MessageService messageService;

// Reviewed and is OK
	@SuppressWarnings("rawtypes")
	@ExceptionHandler()
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Response> handleGeneralExceptions(Exception ex, HttpServletRequest httpRequest) {
		
		logger.error("General Exception : "+ex.getMessage());
		ex.printStackTrace();
		
		String reqBody = ((CustomHttpRequestBody)httpRequest).getBody();
		RequestHeader header = JsonUtils.convertJsonToObject(reqBody, Request.class).getHeader();

		Response response = Response.builder()
				.header(ResponseHeader.builder()
//						.errors(errors)
						.statusCode(ErrorCodes.INTERNAL_SYSTEM_ERROR)
						.message(messageService.getMessage(ErrorCodes.INTERNAL_SYSTEM_ERROR, header!=null?header.getPreferedLanguage():Language.ENGLISH.getValue()))
						.requestId(header!=null?header.getRequestId():"")
						.backendRequestId(header!=null?header.getBackendRequestId():"")
						.path(httpRequest.getServletPath())
						.timestamp(new Date()).build())
				.body(null).build();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	
	@SuppressWarnings("rawtypes")
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Response> handleValidationException(ValidationException ex, HttpServletRequest httpRequest) {
		logger.error("Validation Exception : "+ex.getMessage());
		
		String reqBody = ((CustomHttpRequestBody)httpRequest).getBody();
		RequestHeader header = JsonUtils.convertJsonToObject(reqBody, Request.class).getHeader();
		Map<String, String> errors = new HashMap<>();
		Set<ConstraintViolation<Request>> constraintViolation= ex.getConstraintViolation();
		logger.info("---------------------Constraints-------------------------------");
		constraintViolation.forEach(
			    (constraint) -> {
			    	logger.info(constraint + ",");
			    	logger.info(constraint.getMessage());
			    	errors.put(""+constraint.getPropertyPath(),constraint.getMessage() );
			    	
			    }
			);
		
		Response response = Response.builder().header(ResponseHeader.builder()
				.errors(errors)
				.statusCode(ErrorCodes.VALIDATION_ERROR)
				.message(messageService.getMessage(ErrorCodes.VALIDATION_ERROR, header.getPreferedLanguage()))
				.backendRequestId(header.getBackendRequestId())
				.requestId(header.getRequestId())
				.backendRequestId(header.getBackendRequestId())
				.path(httpRequest.getServletPath())
				.timestamp(new Date())
				.build())
				.body(null).build();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Response> handleJsonParseException(HttpMessageNotReadableException ex) {
		logger.error("JSON Parse Exception : "+ex.getMessage());
		ex.printStackTrace();
		
		Map<String, String> errors = new HashMap<>();
		errors.put(ErrorCodes.INVALID_JSON_OBJECT, messageService.getMessage(ErrorCodes.INVALID_JSON_OBJECT, "EN"));
		Response response = Response.builder()
				.header(ResponseHeader.builder()
				.errors(errors)
				.statusCode(ErrorCodes.GENERIC_FAILURE_ERROR)
				.message(messageService.getMessage(ErrorCodes.GENERIC_FAILURE_ERROR, "EN")).timestamp(new Date()).build())
				.body(null).build();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Response> handleBusinessExceptions(BusinessException ex, HttpServletRequest httpRequest) {
		logger.error("Business Exception : "+ex.getCode());
		
		Map<String, String> errors = new HashMap<>();
		String reqBody = ((CustomHttpRequestBody)httpRequest).getBody();
		RequestHeader header = JsonUtils.convertJsonToObject(reqBody, Request.class).getHeader();
		
		errors.put(ex.getCode(), messageService.getMessage(ex.getCode(), ex.getParams(), header.getPreferedLanguage()));
		Response response = Response.builder()
				.header(ResponseHeader.builder()
					.errors(errors)
					.statusCode(ErrorCodes.BUSINESS_ERROR)
					.message(messageService.getMessage(ErrorCodes.BUSINESS_ERROR, header.getPreferedLanguage()))
					.requestId(header.getRequestId())
					.backendRequestId(header.getBackendRequestId())
					.path(httpRequest.getServletPath())
					.timestamp(new Date()).build())
				.body(null).build();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

/*
	@ExceptionHandler(ResourceAccessException.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Response> handleResourceAccessExceptionException(ResourceAccessException exception,HttpServletRequest httpRequest) {
		logger.info("-----------------------handleResourceAccessExceptionException--------------------");
		return handleGeneralExceptions(exception,httpRequest);
	}

	@ExceptionHandler(HttpClientErrorException.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Response>  handleHttpClientErrorException(HttpClientErrorException exception,HttpServletRequest httpRequest) {
		logger.info("------------------------------Http client exception--------------------------");
		return handleGeneralExceptions(exception,httpRequest);
	}
*/
}
