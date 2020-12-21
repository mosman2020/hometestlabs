package com.hometest.service;

import com.hometest.mybatis.domain.ChangeMobileRequest;
import com.hometest.mybatis.domain.ChangePassword;
import com.hometest.mybatis.domain.LoginUser;
import com.hometest.mybatis.domain.Profile;
import com.hometest.mybatis.domain.User;

public interface UserService {
	
	User getByUsername(LoginUser loginUser) ;
	User createUser(User user) ;
	boolean isUserExists(LoginUser loginUser) ;
	LoginUser generateOtp(LoginUser loginUser) ;
	public boolean updateUserSuccessLoginDetails(User user) ;
	public boolean updateUserFailureLoginDetails(User user) ;
	public boolean lockUser(String username);
	public boolean verifyUser(LoginUser loginUser);
	public boolean changeUserPassword(ChangePassword changepassword);
	public boolean updateUserProfile(Profile profile);
	public ChangeMobileRequest changeUserMobile(ChangeMobileRequest mobile);
	
}
