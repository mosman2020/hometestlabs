/**
 * 
 */
package com.hometest.mybatis.domain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hometest.enums.Gender;
import com.hometest.enums.Title;
import com.hometest.utils.ErrorCodes;
import com.hometest.validation.annotation.ValidEnum;
import com.hometest.validation.groups.OnCreate;
import com.hometest.validation.groups.OnUpdate;

import lombok.Data;

/**
 * @author moosman
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=JsonInclude.Include.NON_EMPTY, content=JsonInclude.Include.NON_NULL)
@Data
public class Profile extends Audit implements Serializable {

	private static final long serialVersionUID = -4907565211226801900L;
	
	private Long profileId;
	
	@Past
	@JsonFormat(shape = JsonFormat.Shape.STRING,  pattern = "mm-dd-yyyy")
	private Date dateBirth;
	
	@NotNull
	private String familyName;
	
	@NotNull
	@Size(min = 4,max = 40,message = ErrorCodes.INVALID_SIZE)
	private String firstName;
	
	
	@NotNull(message = ErrorCodes.MANDATORY_FIELD,
			groups = OnCreate.class)
	@ValidEnum(enumClazz = Gender.class,isAllowNull = true,
			groups = {OnCreate.class,Default.class})
	private String gender;
	
	private String middleName;
	
	@NotNull
	@Pattern(regexp = "^(009665|9665|\\+9665|05|5)(5|0|3|6|4|9|1|8|7)([0-9]{7})$"
						,message = ErrorCodes.INVALID_MOBILE)
	private String mobile;
	
	@ValidEnum(enumClazz = Title.class)
	private String title;
	
}