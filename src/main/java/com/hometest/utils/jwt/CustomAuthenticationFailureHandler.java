/**
 * 
 */
package com.hometest.utils.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.hometest.exceptionhandling.exception.BusinessException;
import com.hometest.mybatis.domain.LoginUser;
import com.hometest.mybatis.domain.User;
import com.hometest.service.UserService;
import com.hometest.utils.ErrorCodes;


/**
 * @author moosman
 *
 */

@Component
public class CustomAuthenticationFailureHandler implements ApplicationListener<AbstractAuthenticationFailureEvent>{

	private Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);
	
	@Autowired
	UserService userService;
		
	@Override
	public void onApplicationEvent(AbstractAuthenticationFailureEvent event) {
		// TODO Auto-generated method stub
		logger.info("from CustomAuthenticationFailureHandler +++++++++++++++++");
		// update user login info with failure login
		String username = event.getAuthentication().getName();
		AuthenticationException ex = event.getException();
		
		if(ex instanceof BadCredentialsException) {
			logger.info("invalid password");
			User user = userService.getByUsername(LoginUser.builder().userName(username).build());
			if(user !=null) {
				userService.updateUserFailureLoginDetails(user);
				if(user.getPassword().getRetryCount()>=3)
					userService.lockUser(username);
			}
			throw new BusinessException(ErrorCodes.INVALID_USER_PASSWORD);
		}if(ex instanceof LockedException) {
			logger.info("user is locked");
			throw new BusinessException(ErrorCodes.USER_LOCKED,new String []{username});
		}if(ex instanceof DisabledException) {
			logger.info("user is deleted");
			throw new BusinessException(ErrorCodes.USER_DELETED,new String []{username});
//		}if(ex instanceof AccountExpiredException) {
//			logger.info("user password is expired");
//			throw new BusinessException(ErrorCodes.USER_PASS_EXPIRED,new String []{username});
		}if(ex instanceof CredentialsExpiredException) {
			logger.info("user password is expired");
			throw new BusinessException(ErrorCodes.USER_PASS_EXPIRED,new String []{username});
		}
		
	}
}