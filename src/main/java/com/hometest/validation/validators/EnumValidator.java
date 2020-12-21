/**
 * 
 */
package com.hometest.validation.validators;

/**
 * @author mosman
 * @Date  Sep 27, 2020
 * @EnumValidator.java
 * @Pacom.hometest.validators
 * @4:41:49 PM
 */
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hometest.validation.annotation.ValidEnum;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

	private static Logger logger = LoggerFactory.getLogger(EnumValidator.class);
	
    List<String> valueList = null;
    boolean isAllowNull = false;
    Class<? extends Enum<?>> enumClass =null;
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    	logger.info("Enum value : "+value);
    	logger.info("Condition : " + (isAllowNull && value==null ));
    	HibernateConstraintValidatorContext context = constraintValidatorContext.unwrap( HibernateConstraintValidatorContext.class );
    	context.addExpressionVariable( "enumvalues", String.join(",", valueList) );
    	if(isAllowNull && (enumClass==null ))
    		return true;
        return valueList.contains(value.toUpperCase());
    }

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        valueList = new ArrayList<String>();
        enumClass = constraintAnnotation.enumClazz();
        
        isAllowNull = constraintAnnotation.isAllowNull();

        @SuppressWarnings("rawtypes")
        Enum[] enumValArr = enumClass.getEnumConstants();

        for (@SuppressWarnings("rawtypes") Enum enumVal : enumValArr) {
            valueList.add(enumVal.toString().toUpperCase());
        }
        
    }

}