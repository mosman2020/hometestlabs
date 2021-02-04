/**
 * 
 */
package com.hometest.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hometest.mybatis.domain.ChangePassword;
import com.hometest.mybatis.domain.ChangeMobileRequest;
import com.hometest.mybatis.domain.LoginUser;
import com.hometest.mybatis.domain.Profile;
import com.hometest.mybatis.domain.User;
import com.hometest.service.MessageService;
import com.hometest.service.UserService;
import com.hometest.utils.ErrorCodes;
import com.hometest.utils.payload.request.Request;
import com.hometest.utils.payload.response.Response;
import com.hometest.utils.payload.response.ResponseHeader;
import com.hometest.validation.groups.OnCreate;

/**
 * @author hometest
 *
 */
@RestController
@RequestMapping("/api/userManagement")
public class UserManagmentController {

	@Autowired
	MessageService messageService;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	UserService userService;

	private Logger logger = LoggerFactory.getLogger(UserManagmentController.class);
/*	
	@PostMapping(value = "/generateOTP" )
	public ResponseEntity<Response> generateOTP(@RequestBody Request<LoginUser> request,  HttpServletRequest httpRequest){
		logger.info("request {} : "+request);
		Response response = Response.builder()
				.header(ResponseHeader.builder()
				.statusCode(ErrorCodes.SUCESS_OPERATION)
	    		.message(messageService.getMessage(ErrorCodes.SUCESS_OPERATION, request.getHeader().getPreferedLanguage()))
	    		.backendRequestId(request.getHeader().getBackendRequestId())
				.requestId(request.getHeader().getRequestId())
	    		.path(httpRequest.getServletPath())
	    		.build())
	    		.body(userService.generateOtp(request.getBody())).build();
	    return ResponseEntity.status(HttpStatus.OK).body(response);

	}
*/	
	@PostMapping(value = "/getUserDetails")
	public ResponseEntity<Response> getByUserName(@RequestBody Request<LoginUser> request, HttpServletRequest httpRequest){
		 logger.info("request {} : "+request);
		 Response response = Response.builder()
				.header(ResponseHeader.builder()
				.statusCode(ErrorCodes.SUCESS_OPERATION)
	    		.message(messageService.getMessage(ErrorCodes.SUCESS_OPERATION, request.getHeader().getPreferedLanguage()))
	    		.backendRequestId(request.getHeader().getBackendRequestId())
				.requestId(request.getHeader().getRequestId())
	    		.path(httpRequest.getServletPath())
	    		.build())
		    	.body(userService.getByUsername(request.getBody())).build();
		 return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	/*
	@PostMapping(value = "/signup")
	@Validated(OnCreate.class)
	public ResponseEntity<Response> registerUser(@RequestBody Request<User> request, HttpServletRequest httpRequest) {
		 logger.info("request {} : "+request);
		 User user = request.getBody();
		 String pass = user.getPassword().getPasswordValue();
		 user.getPassword().setPasswordValue(encoder.encode(pass));
		 Response response = Response.builder()
			  	.header(ResponseHeader.builder()
	  			.statusCode(ErrorCodes.SUCESS_OPERATION)
	    		.message(messageService.getMessage(ErrorCodes.SUCESS_OPERATION, request.getHeader().getPreferedLanguage()))
	    		.backendRequestId(request.getHeader().getBackendRequestId())
				.requestId(request.getHeader().getRequestId())
	    		.path(httpRequest.getServletPath())
	    		.build())
	    		.body(userService.createUser(request.getBody())).build();
	        return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	*/
	@PostMapping(value = "/isUserExists")
	// Add anotation for request sevirity (for audit perpuses)
	public ResponseEntity<Response> isUserExists(@RequestBody Request<LoginUser> request,  HttpServletRequest httpRequest) {
		logger.info("request {} : "+request);
		Response response = Response.builder()
				.header(ResponseHeader.builder()
				.statusCode(ErrorCodes.SUCESS_OPERATION)
	    		.message(messageService.getMessage(ErrorCodes.SUCESS_OPERATION, request.getHeader().getPreferedLanguage()))
	    		.backendRequestId(request.getHeader().getBackendRequestId())
				.requestId(request.getHeader().getRequestId())
	    		.path(httpRequest.getServletPath())	    		
	    		.build())
	    		.body(userService.isUserExists(request.getBody().getUserName())).build();
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
/*	
	@PostMapping(value = "/verifyUser")
	public ResponseEntity<Response> verifyUser(@RequestBody Request<LoginUser> request,  HttpServletRequest httpRequest) {
		logger.info("request {} : "+request);
		logger.info("otp to be validated : "+request.getBody().getOtp());
		boolean result = userService.verifyUser(request.getBody());
		Response response = Response.builder()
				.header(ResponseHeader.builder()
				.statusCode(ErrorCodes.SUCESS_OPERATION)
	    		.message(messageService.getMessage(ErrorCodes.SUCESS_OPERATION, request.getHeader().getPreferedLanguage()))
	    		.backendRequestId(request.getHeader().getBackendRequestId())
				.requestId(request.getHeader().getRequestId())
	    		.path(httpRequest.getServletPath())	    		
	    		.build())
	    		.body(result).build();
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
*/	
	@PostMapping(value = "/changePassword")
	public ResponseEntity<Response> changePassword(@RequestBody Request<ChangePassword> request,  HttpServletRequest httpRequest) {
		logger.info("change password request {} : "+request);
		ChangePassword change = request.getBody();
		boolean result = userService.changeUserPassword(change);
		Response response = Response.builder()
				.header(ResponseHeader.builder()
				.statusCode(ErrorCodes.SUCESS_OPERATION)
	    		.message(messageService.getMessage(ErrorCodes.SUCESS_OPERATION, request.getHeader().getPreferedLanguage()))
	    		.backendRequestId(request.getHeader().getBackendRequestId())
				.requestId(request.getHeader().getRequestId())
	    		.path(httpRequest.getServletPath())	    		
	    		.build())
	    		.body(result).build();
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping(value = "/updateUserProfile")
//	@Validated(OnCreate.class)
	public ResponseEntity<Response> updateUserProfile(@RequestBody Request<Profile> request, HttpServletRequest httpRequest) {
		 logger.info("update user profile request {} : "+request);
		 Response response = Response.builder()
			  	.header(ResponseHeader.builder()
	  			.statusCode(ErrorCodes.SUCESS_OPERATION)
	    		.message(messageService.getMessage(ErrorCodes.SUCESS_OPERATION, request.getHeader().getPreferedLanguage()))
	    		.backendRequestId(request.getHeader().getBackendRequestId())
				.requestId(request.getHeader().getRequestId())
	    		.path(httpRequest.getServletPath())
	    		.build())
	    		.body(userService.updateUserProfile(request.getBody())).build();
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping(value = "/changeUserMobile")
//	@Validated(OnCreate.class)
	public ResponseEntity<Response> changeUserMobile(@RequestBody Request<ChangeMobileRequest> request, HttpServletRequest httpRequest) {
		 logger.info("update user profile request {} : "+request);
		 Response response = Response.builder()
			  	.header(ResponseHeader.builder()
	  			.statusCode(ErrorCodes.SUCESS_OPERATION)
	    		.message(messageService.getMessage(ErrorCodes.SUCESS_OPERATION, request.getHeader().getPreferedLanguage()))
	    		.backendRequestId(request.getHeader().getBackendRequestId())
				.requestId(request.getHeader().getRequestId())
	    		.path(httpRequest.getServletPath())
	    		.build())
	    		.body(userService.changeUserMobile(request.getBody())).build();
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
