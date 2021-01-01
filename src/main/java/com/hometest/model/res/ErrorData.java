/**
 * 
 */
package com.hometest.model.res;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Builder;
import lombok.Data;

/**
 * @author hometest
 *
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonRootName (value = "error")
public class ErrorData {
	private String code;
	private String type;
	private String message;
	private List<Error> Errors;

}
