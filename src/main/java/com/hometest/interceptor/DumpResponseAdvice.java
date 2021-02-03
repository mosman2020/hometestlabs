/**
 * 
 */
package com.hometest.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.hometest.service.MessageDumpService;

/**
 * @author moosman
 *
 */
@ControllerAdvice
public class DumpResponseAdvice implements ResponseBodyAdvice<Object> {

	@Autowired
	MessageDumpService messageDupmService;

	private Logger logger = LoggerFactory.getLogger(DumpResponseAdvice.class);

	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		// TODO Auto-generated method stub
		logger.info("Response Body: {} :" + body.toString());
		
//		if(body instanceof ResponseEntity) {
//			logger.info("body instanceof ResponseEntity");
//			
//		}else {
//			throw new RuntimeException();
//		}
/*
		if (body instanceof Response) {
			ResponseHeader header = ((Response) body).getHeader();
			messageDupmService.insertMessageDump(MessageDump.builder()
					.backendRequestId(header.getBackendRequestId())
					.messageType("Res")
					.statusCode(header.getStatusCode())
					.path(header.getPath())
					.statusDescription(header.getMessage())
					.backendTime(new Date())
					.channelRequestId(header.getRequestId())
					.messageBody(body.toString())
					.channelTime(new Date().toString())
					.build());
			return body;
		} else {
			throw new RuntimeException();
		}
		*/
		return body;
	}
	

}
