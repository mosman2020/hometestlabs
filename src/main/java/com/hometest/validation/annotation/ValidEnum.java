/**
 * 
 */
package com.hometest.validation.annotation;

/**
 * @author mosman
 * @Date  Sep 27, 2020
 * @ValidEnum.java
 * @Pacom.hometest.validators
 * @4:40:49 PM
 */

import com.hometest.utils.ErrorCodes;
import com.hometest.validation.validators.EnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@NotNull(message = ErrorCodes.MANDATORY_FIELD)
@ReportAsSingleViolation
public @interface ValidEnum {

  Class<? extends Enum<?>> enumClazz();

  String message() default ErrorCodes.INVALID_Enum;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
  
  boolean isAllowNull() default false;
  
}


