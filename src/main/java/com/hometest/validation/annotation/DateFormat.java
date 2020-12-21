/**
 * 
 */
package com.hometest.validation.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.hometest.utils.ErrorCodes;
import com.hometest.validation.validators.DateFormatValidator;

/**
 * @author mosman
 * @Date  Sep 27, 2020
 * @DateFormat.java
 * @Pacom.hometest.validators
 * @8:47:09 AM
 */

@Documented
@Constraint(validatedBy = DateFormatValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface DateFormat {
	
	public String message() default ErrorCodes.INVALID_DATE;
	
	public Class<?>[] groups() default { };
	
	public Class<? extends Payload>[] payload() default{};

}