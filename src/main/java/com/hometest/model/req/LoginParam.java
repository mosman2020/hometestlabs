/**
 * 
 */
package com.hometest.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hometest.utils.ErrorCodes;
import com.hometest.validation.groups.OnLogin;
import com.hometest.validation.groups.OnValidateOtp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author mosman
 * @Date  Sep 23, 2020
 * @LoginUser.java
 * @Pacom.hometest.mybatis.domain
 * @6:02:44 PM
 */
@JsonInclude(value=JsonInclude.Include.NON_EMPTY, content=JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginParam implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NotNull (message = ErrorCodes.MANDATORY_FIELD)
	@Email (message = ErrorCodes.INVALID_MAIL)
	private String userName;
	
	@NotNull(groups = OnLogin.class,message = ErrorCodes.MANDATORY_FIELD)
	private String password;
	
	@NotNull(groups = {OnValidateOtp.class})
	private String otp;
}
