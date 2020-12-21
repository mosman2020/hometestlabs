/**
 * 
 */
package com.hometest.validation;

import java.util.Locale;

import javax.validation.MessageInterpolator;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mosman
 * @Date  Oct 2, 2020
 * @CustomMessageInterpolator.java
 * @Pacom.hometest.validation
 * @3:50:38 PM
 */
public class CustomMessageInterpolator implements MessageInterpolator {
	
	private Logger logger = LoggerFactory.getLogger(CustomMessageInterpolator.class);
	
    private final ResourceBundleMessageInterpolator resourceBundleMessageInterpolator;
 
    public CustomMessageInterpolator(ResourceBundleMessageInterpolator resourceBundleMessageInterpolator) {
    	logger.info("----------------- From CustomMessageInterpolator ---------------" );
        this.resourceBundleMessageInterpolator = resourceBundleMessageInterpolator;
    }
 
    @Override
    public String interpolate(String messageTemplate, Context context) {
    	return this.interpolate(messageTemplate,context,Locale.ENGLISH);
    }
 
    @Override
    public String interpolate(String messageTemplate, Context context, Locale locale) {
    	messageTemplate = "{"+messageTemplate+"}";
        return resourceBundleMessageInterpolator.interpolate(messageTemplate, context, locale);
    }
}
