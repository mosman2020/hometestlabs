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
import com.hometest.mybatis.domain.UserPassword;
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
		logger.info("is user exists : "+username);
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
	
}
