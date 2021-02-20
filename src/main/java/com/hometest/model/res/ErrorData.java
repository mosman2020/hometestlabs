/**
 * 
 */
package com.hometest.model.res;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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
