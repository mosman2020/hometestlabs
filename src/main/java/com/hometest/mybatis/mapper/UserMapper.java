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
	
	UserPassword getUserValidOtp(Long userid);
	UserPassword saveUserOtp(UserPassword userpassword);
	
	User getUserByUsername(String username);
	User getUserByUserid(Long userid);
	public int isUserExists(String username);
	public boolean isUserExists(Long userid) ;
	User getUserByProfile(Long profileId);
	boolean updateUserLoginDetails(User user);
	boolean updateOtpRetrycount(Long userid);
	
	boolean updateUserProfile(Profile profile);
	boolean changeUserPassword(ChangePassword changepassword);
	
	int createUser(User user);
	boolean lockUser(String username);
	void unlockUsers();
	boolean expiredOtp(UserPassword password);
	boolean activateUser(Long userid);
	int changeMobile(ChangeMobileRequest mobile);	
	boolean deletePendingChangeMobileRequest(Long userid);
	boolean getChangeMobileRequest(Long userid);
	boolean verifyMobile(Long userId) ;
	boolean updateUserName(User user, String email);
}
