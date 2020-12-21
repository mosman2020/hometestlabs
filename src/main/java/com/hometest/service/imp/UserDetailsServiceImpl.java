package com.hometest.service.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hometest.enums.UserStatus;
import com.hometest.mybatis.dao.UserDao;
import com.hometest.mybatis.domain.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	UserDao userDao;

	private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Override
//	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		logger.info("username from loadUserByUsername {}:"+username);
		
		User user = userDao.getUserByUsername(username);
		if(user ==null)
			throw new UsernameNotFoundException("User Not Found");
		if(UserStatus.CREATED.getValue().equals(user.getUserStatus())|| userDao.getChangeMobileRequest(user.getUserId()))
			user.setMobileVerified(false);
		else
			user.setMobileVerified(true);
		
		return new UserDetailsImpl(user);
	}

}
