/**
 * 
 */
package com.hometest.mybatis.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @author moosman
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=JsonInclude.Include.NON_EMPTY, content=JsonInclude.Include.NON_NULL)
@Data
public class OneTimePassword implements Serializable {

	private static final long serialVersionUID = -7726096757810489169L;
	
	private String username;
	private String otp;
	
}
