package com.hometest.service;

import com.hometest.mybatis.domain.ChangeMobileRequest;
import com.hometest.mybatis.domain.ChangePassword;
import com.hometest.mybatis.domain.LoginUser;
import com.hometest.mybatis.domain.Profile;
import com.hometest.mybatis.domain.User;
import com.hometest.mybatis.domain.UserPassword;

public interface UserService {
	
	User getByUsername(LoginUser loginUser) ;
	User getByUserId(Long userid);
	boolean isUserExists(String username) ;
	public UserPassword generateOtp(Long userid) ;
	public boolean verifyUser(Long userid,String otp);
	
	public boolean updateUserSuccessLoginDetails(User user) ;
	public boolean updateUserFailureLoginDetails(User user) ;
	public boolean lockUser(String username);
		
	public User createUser(User user) ;		
	
	public boolean changeUserPassword(ChangePassword changepassword);
	public boolean updateUserProfile(Profile profile);
	public ChangeMobileRequest changeUserMobile(ChangeMobileRequest mobile);
	
}
