package com.hometest.utils.jwt;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.hometest.service.MessageService;
import com.hometest.utils.ErrorCodes;
import com.hometest.utils.JsonUtils;
import com.hometest.utils.payload.request.Request;
import com.hometest.utils.payload.response.Response;
import com.hometest.utils.payload.response.ResponseHeader;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

	@Autowired
	private MessageService messageService;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.error("------------------------------from commence---------------------");
		authException.printStackTrace();
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();

	        String line;
	        while((line = reader.readLine()) != null){
	            buffer.append(line);
	        }

	        Request req = JsonUtils.convertJsonToObject(buffer.toString(), Request.class);

		logger.error("Unauthorized error: {}", authException.getMessage());

		Map<String, String> errors = new HashMap<>();
		errors.put(ErrorCodes.ACCESS_DENIED, messageService.getMessage(ErrorCodes.ACCESS_DENIED, "EN"));
		Response res = Response.builder()
				.header(ResponseHeader.builder()
				.errors(errors)
				.statusCode(ErrorCodes.GENERIC_FAILURE_ERROR)
				.requestId(req.getHeader().getRequestId())
				.path(req.getHeader().getPath())
				.message(messageService.getMessage(ErrorCodes.GENERIC_FAILURE_ERROR, "EN"))
				.timestamp(new Date()).build())
				.body(null).build();
		String responseAsString = JsonUtils.convertObjectToJsonNode(res).toPrettyString();
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().write(responseAsString);
		response.getWriter().flush();
		response.getWriter().close();
//		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
	}

}
