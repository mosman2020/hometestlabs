/**
 * 
 */
package com.hometest.utils.payload.response;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;

/**
 * @author moosman
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
public class ResponseHeader {

	private String statusCode;
	private String message;
	
	@Builder.Default
	private Date timestamp = new Date();

	private String requestId;
	private String backendRequestId;
	
	private Map<String, String> errors;
	private String path;
	private String token;

}
