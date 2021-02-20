package com.hometest.model.res;

import com.hometest.mybatis.domain.Profile;
import com.hometest.mybatis.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserData {

	private Long userId;
	
	private String userStatus;
	
	private String userType;
	
	private boolean loggedIn;
	
	private String preferedLanguage;
	
	private String userName;
	
	private Date lastLogin;
	
	private boolean mobileVerified;
	
	private Profile profile;

	public UserData(User user){
		this.userId = user.getUserId();
		this.userStatus = user.getUserStatus();
		this.userType = user.getUserType();
		this.loggedIn = user.isLoggedIn();
		this.preferedLanguage = user.getPreferedLanguage();
		this.userName = user.getUserName();
		this.lastLogin = user.getLastLogin();
		this.mobileVerified = user.isMobileVerified();
		this.profile = user.getProfile();
	}
}
