/**
 * 
 */
package com.hometest.utils.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.hometest.mybatis.domain.LoginUser;
import com.hometest.mybatis.domain.User;
import com.hometest.service.UserService;


/**
 * @author moosman
 *
 */

@Component
public class CustomAuthenticationSuccessHandler implements ApplicationListener<AuthenticationSuccessEvent> {

	private Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

	@Autowired
	UserService userService;
		
	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		// TODO Auto-generated method stub
		logger.info("from CustomAuthenticationSuccessHandler----------------------");
		// update user login info with failure login
		String username = event.getAuthentication().getName();
		User user = userService.getByUsername(LoginUser.builder().userName(username).build());
		userService.updateUserSuccessLoginDetails(user);
		
	}
}
