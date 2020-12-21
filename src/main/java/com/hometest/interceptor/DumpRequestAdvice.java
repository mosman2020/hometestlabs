/**
 * 
 */
package com.hometest.interceptor;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import com.hometest.exceptionhandling.exception.ValidationException;
import com.hometest.mybatis.domain.MessageDump;
import com.hometest.service.MessageDumpService;
import com.hometest.service.MessageService;
import com.hometest.utils.payload.request.Request;
import com.hometest.utils.payload.request.RequestHeader;


/**
 * @author moosman
 *
 */
@ControllerAdvice
public class DumpRequestAdvice implements RequestBodyAdvice {

	private Logger logger = LoggerFactory.getLogger(DumpRequestAdvice.class);

	@Autowired
	MessageDumpService messageDupmService;
	
	@Autowired
	Validator validator;
	
	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
		// TODO Auto-generated method stub
		return inputMessage;
	}

	@Override
	public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, 
			Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		
			HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
			
			Request req = (Request) body;
			RequestHeader header = req.getHeader();
			Map<String, String> errors = new HashMap<>();
			
			messageDupmService.insertMessageDump(MessageDump.builder()
				.backendRequestId(header.getBackendRequestId())	
				.backendTime(header.getBackendRequestTime())
				.channelId(header.getChannelId())
				.channelRequestId(header.getRequestId())
				.channelTime(header.getRequestTime())
				.messageType("Req")
				.path(httpRequest.getServletPath())
				.preferedLanguage(header.getPreferedLanguage())
				.messageBody(req.toString())
				.userId(header.getUserId())
				.build());
			  
			Annotation[] annos = parameter.getMethodAnnotations();
			Class<?>[] groups = {Default.class};
			
	         for(int i=0;i<annos.length;i++)
	          {
	        	 Annotation anno = annos[i];
	        	 if(anno instanceof Validated) {
		        	 logger.info(" anno.toString() : "+ anno.toString());
		        	 groups =  ((Validated) anno).value();
	        	 }
	          }
	      
			
		Set<ConstraintViolation<Request>> constraintViolation = validator.validate(req,groups);
		if (!constraintViolation.isEmpty()) {
			logger.info("constraintViolation : "+constraintViolation.toString());
			throw new ValidationException(constraintViolation);
		}
		return body;
	}

	@Override
	public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
			Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		// TODO Auto-generated method stub
		return body;
	}

}
