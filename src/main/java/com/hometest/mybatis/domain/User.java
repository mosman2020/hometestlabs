
package com.hometest.mybatis.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hometest.enums.UserStatus;
import com.hometest.enums.UserType;
import com.hometest.utils.ErrorCodes;
import com.hometest.validation.annotation.ValidEnum;
import com.hometest.validation.groups.OnCreate;
import com.hometest.validation.groups.OnUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author moosman
 *
 */

@JsonInclude(value=JsonInclude.Include.NON_EMPTY, content=JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends Audit implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long userId;
	
	@Null (groups=OnCreate.class)
	@NotNull(groups = OnUpdate.class)
	@ValidEnum(enumClazz = UserStatus.class,groups = OnUpdate.class)
	private String userStatus;
	
	@NotNull (message = ErrorCodes.MANDATORY_FIELD)
	@ValidEnum(enumClazz = UserType.class)
	private String userType;
	
	private boolean loggedIn;
	
	private String preferedLanguage;
	
	@NotNull(message = ErrorCodes.MANDATORY_FIELD)
	@Email (message = ErrorCodes.INVALID_MAIL)
	private String userName;
	
	@Valid
	@JsonIgnore
	private UserPassword password;
	
	private Date lastLogin;
	
	@Valid
	private Profile profile;

	
	private Set<Role> roles = new HashSet<>();
	
	private boolean mobileVerified;
	
}