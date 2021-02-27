/**
 * 
 */
package com.hometest.controllers;

import com.hometest.model.res.Response;
import com.hometest.model.res.TokenData;
import com.hometest.mybatis.domain.*;
import com.hometest.service.AuthenticationService;
import com.hometest.service.UserService;
import com.hometest.utils.ErrorCodes;
import com.hometest.utils.WebConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.*;


/**
 * @author hometest
 *
 */
@RestController
@RequestMapping(UsersController.API_END_POINT)
public class UsersController {

	final static protected String API_END_POINT = WebConfig.API_END_POINT + "/users";

	@Autowired
	UserService userService;
	
	@Autowired
	AuthenticationService authenticationService;
	
	private Logger logger = LoggerFactory.getLogger(UsersController.class);


	@GetMapping(value = "/{id}")
	public ResponseEntity<Response> getByUserId( @PathVariable Long id){
		logger.info("userid : "+id);
		userService.assertLoggedUserIsTheSame(id);
		return ResponseEntity.status(HttpStatus.OK).body(Response.builder().payload(userService.getByUserId(id)).build());
	}

	@GetMapping(value = "/exists/{username}")
	public ResponseEntity isUserExists( @PathVariable String username){
		logger.info("is user exists : "+username);
			if (userService.isUserExists(username))
				return ResponseEntity.status(HttpStatus.OK).build();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@PostMapping(value = "/{id}/otp")
	public ResponseEntity<Response> generateOtp( @PathVariable Long id){
		logger.info("userid : "+id);
		return ResponseEntity.status(HttpStatus.OK).body(Response.builder().payload(userService.generateOtp(id)).build());
	}
	
	@PostMapping(value = "/{id}/verify")
	@ResponseStatus(value = HttpStatus.OK)
	public void verifyUser( @PathVariable Long id ,@RequestBody TokenData otp){
		logger.info("userid : "+id);
		logger.info("otp : "+otp);
		userService.verifyUser(id,otp.getToken());
 	}
	@PostMapping(value = "/signup")
	@ResponseStatus(value = HttpStatus.CREATED)
	public void  signup(@RequestBody User request){
		User user = userService.signup(request);
	}

	@PostMapping(value = "/{id}/changePassword")
	@ResponseStatus(value = HttpStatus.OK)
	public void changePassword(@PathVariable Long id, @RequestBody ChangePassword request){
		logger.info("userid : "+id);
		userService.assertLoggedUserIsTheSame(id);
		userService.changeUserPassword(request);
	}

	@PostMapping(value = "/{id}/updateprofile")
	@ResponseStatus(value = HttpStatus.OK)
	public void updateProfile(@PathVariable Long id, @RequestBody Profile request){
		logger.info("userid : "+id);
		userService.assertLoggedUserIsTheSame(id);
		userService.updateUserProfile(request);
	}

	@PostMapping(value = "/{id}/changemobile")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Response> changeMobileNumber(@PathVariable Long id, @RequestBody ChangeMobileRequest request){
		logger.info("userid : "+id);
		userService.assertLoggedUserIsTheSame(id);
		return ResponseEntity.status(HttpStatus.OK).body(Response.builder().payload(userService.changeUserMobile(request)).build());
	}

	@PostMapping(value = "/{id}/changeemail")
	@ResponseStatus(value = HttpStatus.OK)
	public void changeEmail( @PathVariable Long id, @RequestBody LoginUser user){
		// may be it needs to adjust the validation group to validate username only and bypass password and other fields validation .. pls. check
		logger.info("userid : "+id);
		userService.assertLoggedUserIsTheSame(id);
		userService.changeUserEmail(user.getUserName());
	}
}