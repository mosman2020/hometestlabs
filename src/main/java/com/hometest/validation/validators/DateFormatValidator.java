/**
 * 
 */
package com.hometest.validation.validators;

import com.hometest.validation.annotation.DateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mosman
 * @Date  Sep 27, 2020
 * @DateFormatValidator.java
 * @Pacom.hometest.validators
 * @8:48:38 AM
 */
public class DateFormatValidator implements ConstraintValidator<DateFormat, String>{

	private Logger logger = LoggerFactory.getLogger(DateFormatValidator.class);

	@Override
	public void initialize(DateFormat constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		logger.info("From Date Format Validate");
		
		if(null == value)
			return false;
		try {
			final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Date ndate = formatter.parse(value);
		} catch (ParseException e) {
			logger.error("Invalid Date Exception : "+e.getMessage());
			return false;
		} 
		
		return true;
	}

}
