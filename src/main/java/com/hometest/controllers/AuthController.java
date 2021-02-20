package com.hometest.controllers;

import com.hometest.model.req.LoginParam;
import com.hometest.model.res.Response;
import com.hometest.model.res.TokenData;
import com.hometest.service.AuthenticationService;
import com.hometest.service.UserService;
import com.hometest.service.imp.UserDetailsImpl;
import com.hometest.utils.jwt.JwtUtils;
import com.hometest.validation.groups.OnLogin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(UsersController.API_END_POINT)
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	AuthenticationService authenticationService;

	private Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@PostMapping("/auth")
	@Validated({OnLogin.class})
	public ResponseEntity<Response> authenticateUser(@RequestBody LoginParam user, HttpServletRequest httpRequest) {
		
		logger.info("from authinticate req after converted {} :" + user);
//		LoginUser user = request.getBody();
		Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
		logger.info("from AuthController.authenticateUser()  isAuthenticated():" + authentication.isAuthenticated());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		TokenData token = userDetails.getTokenData();
		token.setToken(jwtUtils.generateJwtToken(authentication));
		userDetails.setToken(token);
		return ResponseEntity.ok().body(Response.builder().payload(token).build());
		
	}

	@PostMapping("/logout")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public  void logout(HttpServletRequest request){
		String token = jwtUtils.parseJwt(request);
		logger.info("token = "+token);
		if(token != null){
			userService.logout(token);
		}
	}
}
