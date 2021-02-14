package com.hometest.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.hometest.mybatis.dao.TokenDao;
import com.hometest.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import com.hometest.model.req.LoginParam;
import com.hometest.model.res.Response;
import com.hometest.model.res.TokenData;
import com.hometest.service.MessageService;
import com.hometest.service.UserService;
import com.hometest.service.imp.UserDetailsImpl;
import com.hometest.utils.jwt.JwtUtils;
import com.hometest.validation.groups.OnLogin;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/users")
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

	@Autowired
	TokenDao tokenDao;

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
		return ResponseEntity.ok().body(Response.builder().payload(token).build());
		
	}

	@PostMapping("/logout")
	public  void logout(){
		TokenData tokenData = authenticationService.getPrinciples().getTokenData();
		if(tokenData != null ){
			tokenDao.insertToken(tokenData.getToken());
		}
	}
}
