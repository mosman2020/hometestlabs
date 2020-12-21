
package com.hometest.mybatis.domain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hometest.utils.ErrorCodes;
import com.hometest.validation.groups.OnCreate;
import com.hometest.validation.groups.OnLogin;
import com.hometest.validation.groups.OnRead;
import com.hometest.validation.groups.OnUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author moosman
 *
 */

@JsonInclude(value=JsonInclude.Include.NON_EMPTY, content=JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPassword extends Audit implements Serializable{

	private static final long serialVersionUID = -3207104642088836652L;
	
	private Long passwordId;
	private Long userId;
		
	@NotNull (groups = {OnCreate.class},message = ErrorCodes.MANDATORY_FIELD)
	@Null(groups=OnUpdate.class)
	private String passwordValue;
	
	private int retryCount;
	
//	@NotNull(groups=OnCreate.class,message = ErrorCodes.MANDATORY_FIELD)
//	@Null(groups=OnUpdate.class)
//	private String confirmPassword;
	
	@DateTimeFormat
	private Date expiryDate;
	
	@NotNull
	private String passwordType;
	
}
