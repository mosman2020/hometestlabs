package com.hometest.mybatis.domain;

import com.hometest.utils.ErrorCodes;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author mosman
 * @File : ChangeMobile.java
 * @Project : HomeTestLabs
 * @Pakage : com.hometest.mybatis.domain
 * @Date : 25 Nov 2020  01:43:27
 * @Version : 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChangeMobileRequest extends Audit {

private static final long serialVersionUID = 1L;
	
	private Long requestId;
	private Long userId;
	
	@NotNull
	@Pattern(regexp = "^(009665|9665|\\+9665|05|5)(5|0|3|6|4|9|1|8|7)([0-9]{7})$"
						,message = ErrorCodes.INVALID_MOBILE)
	private String oldValue;
	@NotNull
	@Pattern(regexp = "^(009665|9665|\\+9665|05|5)(5|0|3|6|4|9|1|8|7)([0-9]{7})$"
						,message = ErrorCodes.INVALID_MOBILE)
	private String newValue;
	
	private String status;
	private String type;
}