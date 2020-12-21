/**
 * 
 */
package com.hometest.utils.payload.request;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Currency;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hometest.enums.Channel;
import com.hometest.enums.Language;
import com.hometest.utils.ErrorCodes;
import com.hometest.validation.annotation.DateFormat;
import com.hometest.validation.annotation.ValidEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author moosman
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@NoArgsConstructor  // important in case of @JsonFormat is used with Field
@AllArgsConstructor // important in case of @JsonFormat is used with Field
public class RequestHeader {
	@NotNull(message = ErrorCodes.MANDATORY_FIELD)
	@JsonProperty("userId")
	private String userId;

	@JsonProperty("requestTime")
	@DateFormat
	private String requestTime;

	@JsonProperty("backendRequestTime")
	private final Date backendRequestTime = new Date();

	@JsonProperty("backendRequestId")
	private final String backendRequestId = ""+UUID.randomUUID();

	@NotNull(message = ErrorCodes.MANDATORY_FIELD)
	@JsonProperty("requestId")
	private String requestId;
	
	@ValidEnum(enumClazz = Language.class)
	private String preferedLanguage;

	@NotNull(message = ErrorCodes.MANDATORY_FIELD)
	@JsonProperty("channelId")
	
	@ValidEnum(enumClazz = Channel.class)
	private String channelId;

	@JsonProperty("path")
	private String path;

}
