/**
 * 
 */
package com.hometest.service.imp;

import com.hometest.enums.ChangeMobileStatus;
import com.hometest.enums.ChangeMobileType;
import com.hometest.enums.UserStatus;
import com.hometest.exceptionhandling.exception.BusinessException;
import com.hometest.mybatis.dao.TokenDao;
import com.hometest.mybatis.dao.UserDao;
import com.hometest.mybatis.domain.*;
import com.hometest.service.AuthenticationService;
import com.hometest.service.UserService;
import com.hometest.utils.ErrorCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author moosman
 *
 */
@Service
public class UserServiceImp implements UserService {

	final private UserDao userDao;

	final private AuthenticationService authenticationService;

	final private TokenDao tokenDao;

	
	private Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

	@Autowired
	public UserServiceImp(UserDao userDao, AuthenticationService authenticationService, TokenDao tokenDao) {
		this.userDao = userDao;
		this.authenticationService = authenticationService;
		this.tokenDao = tokenDao;
	}

	@Override
	public User getByUsername(LoginUser loginUser) {
		return userDao.getUserByUsername(loginUser.getUserName());
	}
	
	@Override
	public User getByUserId(Long userid) {
		if(!userDao.isUserExists(userid))
			throw new BusinessException(ErrorCodes.NO_DATA_FOUND);
		return userDao.getUserByUserid(userid);
	}
	
	public boolean isUserExists(String username) {
		int isUserExists = userDao.isUserExists(username);
		return isUserExists == 1;
	}

	@Override
	public UserPassword generateOtp(Long userid) {
		 if(!userDao.isUserExists(userid))
			 throw new BusinessException(ErrorCodes.USER_NOT_EXISTS);
		 int randomPin   =(int) (Math.random()*9000)+1000; 
         String otp  = String.valueOf(randomPin); 
		 logger.info("otp : "+otp);
		 UserPassword password = new UserPassword();
		 password.setUserId(userid);
		 password.setPasswordType("temp");
		 password.setPasswordValue(otp);
		 password.setExpiryDate(new Date(System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(5)));
		 password.setCreatedBy((long)1);
		 password.setCreatedDate(new Date());
		 return userDao.saveUserOtp(password);
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
	public void verifyUser(Long userid,String otp) {

		logger.info("verify user");
		UserPassword password = userDao.getUserValidOtp(userid);
		logger.info("password : "+password);
		boolean b = false;
		if(password!=null) {
			boolean bb = otp.equals(password.getPasswordValue()!=null?password.getPasswordValue().trim():"");
			logger.info("bb:"+bb);
			if(bb) {
				b = userDao.activateUser(userid);
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
					userDao.updateOtpRetrycount(userid);
					
				}
			}
			
		}else {
			b = false;
		}
		if(!b){
			//TODO check again
			throw new BusinessException(ErrorCodes.USER_NOT_VERIFIED);
		}
	}

	@Override
	public boolean changeUserPassword(ChangePassword changepassword) {
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
		User user = authenticationService.getUser();
		Long userId = user.getUserId();
		logger.info("birth date : "+profile.getDateBirth());
		User retrivedUser = userDao.getUserByUserid(userId);
		logger.info("retrivie user : "+retrivedUser);
		if(retrivedUser!=null && UserStatus.ACTIVE.getValue().equals(retrivedUser.getUserStatus())) {
			profile.setUpdatedBy(userId);
			profile.setUpdatedDate(new Date());
			profile.setId(userDao.getUserProfile(user).getId());
			boolean success =  userDao.updateUserProfile(profile);
			if(success){
				saveProfileAddress(profile.getAddresses(), retrivedUser);
			}
		}
		return false;
	}

	public boolean saveProfileAddress(List<Address> addresses, User user) {
		Profile profile = userDao.getUserProfile(user);
		for (Address address : addresses) {
			userDao.saveProfileAddress(address, profile.getId());
		}
		return true;
	}

	@Override
	public ChangeMobileRequest changeUserMobile(ChangeMobileRequest mobile) {
		Long userId = authenticationService.getUser().getUserId();
		mobile.setStatus(ChangeMobileStatus.PENDING.getValue());
		mobile.setCreatedBy((long)1);
		mobile.setCreatedDate(new Date());
		mobile.setType(ChangeMobileType.MOBILE.getValue());
		boolean b = userDao.deletePendingChangeMobileRequest(userId);
		logger.info("pending requests deleted : "+b);
		int requestId = userDao.changeMobile(mobile);

		// generate OTP
		generateOtp(userId);
		return mobile;
	}

	@Override
	public User signup(User request) {

		validateUsername(request.getUserName());

		request.getPassword().setPasswordType("permanent");
		userDao.createUser(request);
		// generate OTP
		generateOtp(request.getUserId());
		return request;
	}

	@Override
	public void changeUserEmail(String email) {
		User user = authenticationService.getUser();
		user.setUserName(email);
		userDao.updateUserName(user, email);

		// generate OTP
		generateOtp(user.getUserId());
	}

	@Override
	public void logout(String token) {
		TokenBlackList tokenBlackList = TokenBlackList
									.builder()
									.token(token)
									.user(authenticationService.getPrinciples().getUser())
							  .build();
		tokenDao.insertToken(tokenBlackList);
	}

	@Override
	public void assertLoggedUserIsTheSame(Long id) {
		User user = authenticationService.getUser();
		if(id !=user.getUserId()) {
			throw new InsufficientAuthenticationException(ErrorCodes.INSUFFICIENT_PRIVILEGES);
		}
	}

	private void validateUsername(String userName){
		if(isUserExists(userName))
			throw new BusinessException(ErrorCodes.USER_EXISTS, new String []{userName});
	}
}
