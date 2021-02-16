/**
 * 
 */
package com.hometest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hometest.model.res.Response;
import com.hometest.model.res.TokenData;
import com.hometest.mybatis.domain.ChangeMobileRequest;
import com.hometest.mybatis.domain.ChangePassword;
import com.hometest.mybatis.domain.LoginUser;
import com.hometest.mybatis.domain.Profile;
import com.hometest.mybatis.domain.User;
import com.hometest.service.AuthenticationService;
import com.hometest.service.UserService;
import com.hometest.utils.ErrorCodes;

import java.util.Map;

/**
 * @author hometest
 *
 */
@RestController
@RequestMapping("/api/v1")
public class UsersController {

	@Autowired
	UserService userService;
	
	@Autowired
	AuthenticationService authenticationService;
	
	private Logger logger = LoggerFactory.getLogger(UsersController.class);


	private void assertLoggedUserIsTheSame(Long id) {
		User user = authenticationService.getUser();
		if(id !=user.getUserId()) {
			throw new InsufficientAuthenticationException(ErrorCodes.INSUFFICIENT_PRIVILEGES);
		}
	}

	@GetMapping(value = "/users/{id}")
	public ResponseEntity<Response> getByUserId( @PathVariable Long id){
		logger.info("userid : "+id);
		assertLoggedUserIsTheSame(id);
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
	@PostMapping(value = "/users/signup")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<Response> signup(@RequestBody User request){
		User user = userService.signup(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(Response.builder().payload(TokenData.builder().userid(user.getUserId()).build()).build());
	}

	@PostMapping(value = "/users/{id}/changePassword")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void changePassword(@PathVariable Long id, @RequestBody ChangePassword request){
		logger.info("userid : "+id);
		assertLoggedUserIsTheSame(id);
		userService.changeUserPassword(request);
	}

	@PostMapping(value = "/users/{id}/updateprofile")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void updateProfile(@PathVariable Long id, @RequestBody Profile request){
		logger.info("userid : "+id);
		assertLoggedUserIsTheSame(id);
		userService.updateUserProfile(request);
	}

	@PostMapping(value = "/users/{id}/changemobile")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Response> changeMobileNumber(@PathVariable Long id, @RequestBody ChangeMobileRequest request){
		logger.info("userid : "+id);
		assertLoggedUserIsTheSame(id);
		return ResponseEntity.status(HttpStatus.OK).body(Response.builder().payload(userService.changeUserMobile(request)).build());
	}

	@PostMapping(value = "/users/{id}/changeemail")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void changeEmail( @PathVariable Long id, @RequestBody LoginUser user){
		// may be it needs to adjust the validation group to validate username only and bypass password and other fields validation .. pls. check
		logger.info("userid : "+id);
		assertLoggedUserIsTheSame(id);
		userService.changeUserEmail(user.getUserName());
	}
}