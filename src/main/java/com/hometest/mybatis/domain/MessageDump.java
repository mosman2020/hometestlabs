/**
 * 
 */
package com.hometest.mybatis.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

/**
 * @author moosman
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=JsonInclude.Include.NON_EMPTY, content=JsonInclude.Include.NON_NULL)
@Data
@Builder
public class MessageDump implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String messageType;
	private String userId;
	private String backendRequestId;
	private String channelRequestId;
	private Date backendTime;
	private String channelTime;
	private String preferedLanguage;
	private String channelId;
	private String statusCode;
	private String statusDescription;	
	private String messageBody;
	private String path;
	
}
