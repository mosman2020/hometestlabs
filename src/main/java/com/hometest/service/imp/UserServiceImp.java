/**
 * 
 */
package com.hometest.service.imp;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hometest.enums.ChangeMobileStatus;
import com.hometest.enums.ChangeMobileType;
import com.hometest.enums.UserStatus;
import com.hometest.exceptionhandling.exception.BusinessException;
import com.hometest.mybatis.dao.UserDao;
import com.hometest.mybatis.domain.ChangeMobileRequest;
import com.hometest.mybatis.domain.ChangePassword;
import com.hometest.mybatis.domain.LoginUser;
import com.hometest.mybatis.domain.Profile;
import com.hometest.mybatis.domain.User;
import com.hometest.mybatis.domain.UserPassword;
import com.hometest.service.UserService;
import com.hometest.utils.ErrorCodes;

/**
 * @author moosman
 *
 */
@Service
public class UserServiceImp implements UserService {

	@Autowired
	UserDao userDao;
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

	@Override
	public User getByUsername(LoginUser loginUser) {
		User rsBody = null;
		try {
			rsBody = userDao.getUserByUsername(loginUser.getUserName());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("E000003");
		}
		
		return rsBody;
	}

	@Override
	public User createUser(User user) {
		logger.info("Create user :" + user);
		user.setUserStatus(UserStatus.CREATED.getValue());
		if(!isUserExists(LoginUser.builder().userName(user.getUserName()).build())){
			user.setCreatedBy((long)1);
			user.setCreatedDate(new Date());
			user.getPassword().setPasswordType("permanent");
			user.getPassword().setCreatedBy((long)1);
			user.getPassword().setCreatedDate(new Date());
			user.getProfile().setCreatedBy((long)1);
			user.getProfile().setCreatedDate(new Date());
			userDao.createUser(user);
		}else {
			throw new BusinessException(ErrorCodes.USER_EXISTS,new String[] {user.getUserName()});
		}
		return user;
	}

	public boolean isUserExists(LoginUser loginUser) {
		int isUserExists = userDao.isUserExists(loginUser.getUserName().trim());
		return isUserExists == 1;
	}


	@Override
	public LoginUser generateOtp(LoginUser loginUser) {

		 int randomPin   =(int) (Math.random()*9000)+1000; 
         String otp  = String.valueOf(randomPin); 
		 logger.info("otp : "+otp);
		 UserPassword password = new UserPassword();
		 password.setPasswordType("temp");
		 password.setPasswordValue(otp);
		 password.setExpiryDate(new Date(System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(5)));
		 password.setCreatedBy((long)1);
		 password.setCreatedDate(new Date());
		 //update database by adding the otp
		User user = User.builder().userName(loginUser.getUserName()).password(password).build();
		if(userDao.saveUserOtp(user))
		 loginUser.setOtp(otp);
		return loginUser;
	}
	
	@Override
	public boolean updateUserSuccessLoginDetails(User user) {
		user.setLastLogin(new Date());
		user.setLoggedIn(true);
		user.getPassword().setRetryCount(0);
		return userDao.updateUserLoginDetails(user);
	}
	
	@Override
	public boolean updateUserFailureLoginDetails(User user) {
		user.setLastLogin(new Date());
		user.setLoggedIn(false);
		user.getPassword().setRetryCount(user.getPassword().getRetryCount()+1);
		return userDao.updateUserLoginDetails(user);
	}

	@Override
	public boolean lockUser(String username) {
		return userDao.lockUser(username);
	}

	@Override
	public boolean verifyUser(LoginUser loginUser) {
		// TODO Auto-generated method stub
		logger.info("verify user");
		UserPassword password = userDao.validateOtp(loginUser.getUserName(),loginUser.getOtp());
		logger.info("password : "+password);
		boolean b = false;
		if(password!=null) {
			boolean bb = loginUser.getOtp().equals(password.getPasswordValue()!=null?password.getPasswordValue().trim():"");
			if(bb) {
				b = userDao.activateUser(loginUser.getUserName());
			}else {
				b = false;
				logger.info("saved otp = "+password.getPasswordValue());
				logger.info("retry count  = "+password.getRetryCount());
				logger.info("password id : "+password.getPasswordId());
				//update retrycount for otp
				if(password.getRetryCount()>=3) {
					password.setExpiryDate(new Date());
					userDao.expiredOtp(password);
				}else {
					userDao.updateOtpRetrycount(loginUser.getUserName());
				}
			}
			
		}else {
			b = false;
		}
		return b;
	}

	@Override
	public boolean changeUserPassword(ChangePassword changepassword) {
		// TODO Auto-generated method stub
		PasswordEncoder passencoder = new BCryptPasswordEncoder();
		User user = userDao.getUserByUsername(changepassword.getUserName());
		logger.info("old passowrd      :"+changepassword.getOldPassword()+":");
		logger.info("retrived passowrd :"+user.getPassword().getPasswordValue()+":");
		logger.info("password matcher : "+passencoder.matches(changepassword.getOldPassword(), user.getPassword().getPasswordValue()));
	    if(!passencoder.matches(changepassword.getOldPassword(), user.getPassword().getPasswordValue())) {
	    	logger.info("incorrect password");
			throw new BusinessException(ErrorCodes.INVALID_USER_PASSWORD);
	    }
		if(changepassword.getNewPassword().equals(changepassword.getOldPassword())) {
	    	logger.info("old password and new passowrd are matched");
			throw new BusinessException(ErrorCodes.OLD_NEW_PASSWORD_MATCHED);
	    }
			changepassword.setNewPassword(passencoder.encode(changepassword.getNewPassword()));
			changepassword.setUpdatedBy(user.getUserId());
			changepassword.setUpdatedDate(new Date());
		return userDao.changeUserPassword(changepassword);
	}

	@Override
	public boolean updateUserProfile(Profile profile) {
		// TODO Auto-generated method stub
		logger.info("birth date : "+profile.getDateBirth());
		User retrivedUser = userDao.getUserByProfile(profile.getProfileId());
		logger.info("retrivie user : "+retrivedUser);
		if(retrivedUser!=null && UserStatus.ACTIVE.getValue().equals(retrivedUser.getUserStatus())) {
			profile.setUpdatedBy((long)1);
			profile.setUpdatedDate(new Date());
			return userDao.updateUserProfile(profile);
		}
		return false;
	}

	@Override
	public ChangeMobileRequest changeUserMobile(ChangeMobileRequest mobile) {
		mobile.setStatus(ChangeMobileStatus.PENDING.getValue());
		mobile.setCreatedBy((long)1);
		mobile.setCreatedDate(new Date());
		mobile.setType(ChangeMobileType.MOBILE.getValue());
		boolean b = userDao.deletePendingChangeMobileRequest(mobile.getUserId());
		logger.info("pending requests deleted : "+b);
		int requestId = userDao.changeMobile(mobile);
		return mobile;
	}
	
}
