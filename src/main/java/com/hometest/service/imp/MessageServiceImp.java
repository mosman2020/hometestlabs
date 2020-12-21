/**
 * 
 */
package com.hometest.service.imp;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.hometest.service.MessageService;

/**
 * @author moosman
 *
 */
@Service
public class MessageServiceImp implements MessageService{
	@Autowired
    private MessageSource messageSource;

    @Override
    public String getMessage(String id, Object[] params,String lang) {
    	if(params==null)
    		getMessage(id,lang);
        Locale locale = new Locale(lang);
        String errorMessage = messageSource.getMessage(id, params,locale);  
        return errorMessage;
    }
    @Override
    public String getMessage(String id, String lang) {
        Locale locale = new Locale(lang);
        String errorMessage = messageSource.getMessage(
                id, new Object[] {},locale);  
        return errorMessage;
    }
}
