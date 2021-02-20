/**
 * 
 */
package com.hometest.utils.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

/**
 * @author moosman
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@NoArgsConstructor  // important in case of @JsonFormat is used with Field
@AllArgsConstructor // important in case of @JsonFormat is used with Field
public class Request<T> {
	@Valid
	@JsonProperty("header")
	private RequestHeader header;

	@Valid
	@JsonProperty("body")
	private T body;
	
}