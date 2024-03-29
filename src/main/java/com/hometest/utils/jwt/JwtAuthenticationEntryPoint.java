package com.hometest.utils.jwt;

import com.hometest.model.res.ErrorData;
import com.hometest.service.MessageService;
import com.hometest.utils.ErrorCodes;
import com.hometest.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

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
		logger.error("Unauthorized error: {}", authException.getMessage());

//		List<Error> errors = new ArrayList<Error>();
//		errors.add(Error.builder().code(ErrorCodes.ACCESS_DENIED).message(authException.getMessage()).build());
		ErrorData errorData = ErrorData.builder().code(ErrorCodes.ACCESS_DENIED).message(authException.getMessage()).build();
		String responseAsString = JsonUtils.convertObjectToJsonNode(errorData).toPrettyString();
		
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		response.getWriter().write(responseAsString);
		response.getWriter().flush();
		response.getWriter().close();
//		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
	}

}
