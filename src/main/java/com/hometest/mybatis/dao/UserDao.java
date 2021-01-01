/**
 * 
 */
package com.hometest.mybatis.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hometest.mybatis.mapper.UserMapper;
import com.hometest.mybatis.domain.ChangeMobileRequest;
import com.hometest.mybatis.domain.ChangePassword;
import com.hometest.mybatis.domain.Profile;
import com.hometest.mybatis.domain.User;
import com.hometest.mybatis.domain.UserPassword;

/**
 * @author moosman
 *
 */
@Component
public class UserDao implements UserMapper{

	@Autowired
	private SqlSession sqlSession;

	private Logger logger = LoggerFactory.getLogger(UserDao.class);

	@Override
	public User getUserByUsername(String username) {
		User user = this.sqlSession.selectOne("getUserByUsername", username);
		logger.info("user: "+user);
		return user;
	}
	
	@Override
	public User getUserByUserid(Long userid) {
		return this.sqlSession.selectOne("getUserByUserid", userid);
	}
	
	@Override
	public UserPassword getUserValidOtp(Long userid) {
		logger.info("get user otp");
		UserPassword password = this.sqlSession.selectOne("getUserOtp", userid);
		logger.info("passwords:"+password);
		return password;
	}

	@Override
	public UserPassword saveUserOtp(UserPassword userpassword) {
		// TODO Auto-generated method stub
		logger.info("from saveUserOpt dao");
		if(1==this.sqlSession.insert("saveUserOtp",userpassword)) {
			logger.info("otp object :"+userpassword);
			return  userpassword;
		}
		return null;
	}

	@Override
	public boolean updateUserProfile(Profile profile) {
		logger.info("update user profile");
		return 1== this.sqlSession.update("updateUserProfile",profile);
	}

	@Override
	public boolean changeUserPassword(ChangePassword changepassword) {
		// TODO Auto-generated method stub
		logger.info("change password");
		logger.info("new password : "+changepassword.getNewPassword());
		int i = this.sqlSession.update("changePassword",changepassword);
		logger.info("i : "+i);
		return 1==i;
	}

	@Override
	public boolean updateUserLoginDetails(User user) {
		logger.info("update user login details");
		int firstUpdate = this.sqlSession.update("updateUserLoginDetails",user);
		int secondUpdate = this.sqlSession.update("updateUserRetrycount",user);
		return firstUpdate==secondUpdate;
	}
	
	@Override
	@Transactional (propagation = Propagation.REQUIRED ,isolation = Isolation.DEFAULT,timeout=36000, rollbackFor = Exception.class)
	public int createUser(User user) {
		Profile profile = user.getProfile();
		logger.info("profile before insertion : "+profile);
		this.sqlSession.insert("insertUserProfile",profile);
		logger.info("profile after insertion : "+profile);
		logger.info("profile after insertion2 : "+user.getProfile());
//		user.setProfile(profile);
		logger.info("user before insertion :"+user);
		int userId = this.sqlSession.insert("insertUser",user);
		logger.info("user after insertion :"+user);
		UserPassword password = user.getPassword();
		password.setUserId(user.getUserId());
		logger.info("userId == "+user.getUserId());
		logger.info("passowrd before insertion :"+password);
		this.sqlSession.insert("insertUserPassword",password);
		logger.info("passowrd after insertion :"+password);
		logger.info("passowrd before insertion2 :"+user.getPassword());
//		this.sqlSession.commit();
//		this.sqlSession.rollback();
		return userId;
	}

	
	@Override
	public int isUserExists(String username) {
		int numberOfExistance = this.sqlSession.selectOne("isUserExists", username);
		return numberOfExistance;
	}

	public boolean isUserExists(Long userid) {
		int numberOfExistance = this.sqlSession.selectOne("isUserExistsByid", userid);
		return 1==numberOfExistance;
	}
	
	@Override
	public boolean lockUser(String username) {
		Map params = new HashMap();
		params.put("userName", username);
		params.put("updateDate", new Date());
		params.put("updatedBy", 1);
		return 1==this.sqlSession.update("lockUser",params);// TODO Auto-generated method stub
		
	}

	@Override
	public void unlockUsers() {
		logger.info("unlock users dao ");
		this.sqlSession.update("unlockUsers");
	}

	public void deleteExpiredOtp() {
		logger.info("delete expired otp dao");
		this.sqlSession.delete("deleteExpiredOtp");
	}

	@Override
	public boolean updateOtpRetrycount(Long userid) {
		// TODO Auto-generated method stub
		logger.info("update otp retry count");
		return 1==this.sqlSession.update("updateOtpRetrycount",userid);
	}

	@Override
	public boolean expiredOtp(UserPassword password) {
		// TODO Auto-generated method stub
		logger.info("expir otp ");
		
		return 1== this.sqlSession.update("expirOtp",password);
	}

	@Override
	public boolean activateUser(Long userid) {
		// TODO Auto-generated method stub
		logger.info("activate user");
		return 1== this.sqlSession.update("activateUser",userid);
	}

	@Override
	public User getUserByProfile(Long profileId) {
		logger.info("get user details by profile id");
		logger.info("profile Id : "+profileId);
		return this.sqlSession.selectOne("getUserByProfile",profileId);
	}

	@Override
	public int changeMobile(ChangeMobileRequest mobile) {
		// TODO Auto-generated method stub
		logger.info("change mobile number");
		int requestId = this.sqlSession.insert("addChangeMobileRequest", mobile);
		return requestId;
	}
	
	public boolean deletePendingChangeMobileRequest(Long userid) {
		logger.info("delete all pending change mobile requests");
		return 1== this.sqlSession.update("deletePendingChangeMobileRequest",userid);
	}
	
	public boolean getChangeMobileRequest(Long userid) {
		logger.info("get pending change mobile request if any");
		int result = this.sqlSession.selectOne("getChangeMobileRequestCount",userid);
		return 1== result;
	}
	
	@Override
	@Transactional (propagation = Propagation.REQUIRED ,isolation = Isolation.DEFAULT,timeout=36000, rollbackFor = Exception.class)
	public boolean verifyMobile(Long userId) {
		logger.info("verify new mobile number");
		ChangeMobileRequest changeMobile = this.sqlSession.selectOne("getChangeMobileRequest", userId);
		return false;
	}
}
