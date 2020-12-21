/**
 * 
 */
package com.hometest.mybatis;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.*;

/**
 * @author moosman
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EnumValue {
    String value();
}