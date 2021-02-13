/**
 * 
 */
package com.hometest.mybatis.mapper;

import com.hometest.mybatis.domain.*;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author moosman
 *
 */
@Mapper
public interface UserMapper {
	
	UserPassword getUserValidOtp(Long userid);
	UserPassword saveUserOtp(UserPassword userpassword);

	User getUserByUsername(String username);
	Profile getUserProfile(User user);
	User getUserByUserid(Long userid);
	public int isUserExists(String username);
	public boolean isUserExists(Long userid) ;
	User getUserByProfile(Long profileId);
	boolean updateUserLoginDetails(User user);
	boolean updateOtpRetrycount(Long userid);
	
	boolean updateUserProfile(Profile profile, Long userId);
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

    boolean saveProfileAddress(Address address, Long profileId);
}
