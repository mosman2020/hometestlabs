/**
 * 
 */
package com.hometest.utils.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author moosman
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
public class Response<T> {

	@JsonProperty("header")
	private ResponseHeader header;

	@JsonProperty("body")
	private T body;
	
}
