package com.hometest.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hometest.mybatis.domain.LoginUser;
import com.hometest.service.MessageService;
import com.hometest.service.UserService;
import com.hometest.service.imp.UserDetailsImpl;
import com.hometest.utils.ErrorCodes;
import com.hometest.utils.jwt.JwtUtils;
import com.hometest.utils.payload.request.Request;
import com.hometest.utils.payload.response.Response;
import com.hometest.utils.payload.response.ResponseHeader;
import com.hometest.validation.groups.OnLogin;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	MessageService messageService;
		
	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	private Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@PostMapping("/signin")
	@Validated({OnLogin.class})
	public Response authenticateUser(@RequestBody Request<LoginUser> request, HttpServletRequest httpRequest) {
		
		logger.info("from authinticate req after converted {} :" + request);
		LoginUser user = request.getBody();
		Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
		logger.info("from AuthController.authenticateUser()  isAuthenticated():" + authentication.isAuthenticated());
		SecurityContextHolder.getContext().setAuthentication(authentication);
			
		// update user login info with success login
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		Response response = Response.builder()
				.header(ResponseHeader.builder()
				.statusCode(ErrorCodes.SUCESS_OPERATION)
				.message(messageService.getMessage(ErrorCodes.SUCESS_OPERATION, request.getHeader().getPreferedLanguage()))
	    		.backendRequestId(request.getHeader().getBackendRequestId())
				.requestId(request.getHeader().getRequestId())
	    		.path(httpRequest.getContextPath())
	    		.token(jwtUtils.generateJwtToken(authentication))
	    		.build())
		    	.body(userDetails.getUser()).build();
		return response;
	}
	
}
