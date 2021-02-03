/**
 * 
 */
package com.hometest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hometest.model.res.Response;
import com.hometest.model.res.TokenData;
import com.hometest.service.MessageService;
import com.hometest.service.UserService;

/**
 * @author hometest
 *
 */
@RestController
@RequestMapping("/api/v1")
public class UsersController {
	@Autowired
	MessageService messageService;
		
	@Autowired
	UserService userService;

	private Logger logger = LoggerFactory.getLogger(UsersController.class);
	
	@GetMapping(value = "/users/{id}")
	public ResponseEntity<Response> getByUserId( @PathVariable Long id){
		logger.info("userid : "+id);
		 return ResponseEntity.status(HttpStatus.OK).body(Response.builder().payload(userService.getByUserId(id)).build());
	}
	
	@GetMapping(value = "/users/exists/{username}")
	public ResponseEntity isUserExists( @PathVariable String username){
		logger.info("is user exists :"+username+":");
		logger.info("is exists: "+userService.isUserExists(username));
			if (userService.isUserExists(username))
				return ResponseEntity.status(HttpStatus.OK).build();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@PostMapping(value = "/users/{id}/otp")
	public ResponseEntity<Response> generateOtp( @PathVariable Long id){
		logger.info("userid : "+id);
		return ResponseEntity.status(HttpStatus.OK).body(Response.builder().payload(userService.generateOtp(id)).build());
	}
	
	@PostMapping(value = "/users/{id}/verify")
	public ResponseEntity<Response> verifyUser( @PathVariable Long id ,@RequestBody TokenData otp){
		logger.info("userid : "+id);
		logger.info("otp : "+otp);
//		if(userService.verifyUser(id,otp.getToken()))
			return ResponseEntity.status(HttpStatus.OK).body(Response.builder().build());
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
	 */
	
}
