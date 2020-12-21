/**
 * 
 */
package com.hometest.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.hometest.mybatis.domain.ChangeMobileRequest;
import com.hometest.mybatis.domain.ChangePassword;
import com.hometest.mybatis.domain.Profile;
import com.hometest.mybatis.domain.User;
import com.hometest.mybatis.domain.UserPassword;

/**
 * @author moosman
 *
 */
@Mapper
public interface UserMapper {
	
	UserPassword validateOtp(String username, String otp);
	boolean saveUserOtp(User user);
	User getUserByUsername(String username);
	int isUserExists(String username);
	User getUserByProfile(Long profileId);
	boolean updateUserLoginDetails(User user);
	boolean updateOtpRetrycount(String username);
	
	boolean updateUserProfile(Profile profile);
	boolean changeUserPassword(ChangePassword changepassword);
	
	int createUser(User user);
	boolean lockUser(String username);
	void unlockUsers();
	boolean expiredOtp(UserPassword password);
	boolean activateUser(String username);
	int changeMobile(ChangeMobileRequest mobile);	
	boolean deletePendingChangeMobileRequest(Long userid);
	public boolean getChangeMobileRequest(Long userid);
	public boolean verifyMobile(Long userId) ;
}
