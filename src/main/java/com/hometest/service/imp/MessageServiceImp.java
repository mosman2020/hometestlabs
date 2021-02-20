/**
 * 
 */
package com.hometest.service.imp;

import com.hometest.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

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
    
    @Override
    public String getMessage(String id) {
        String errorMessage = messageSource.getMessage(
                id, new Object[] {},Locale.US);  
        return errorMessage;
    }
}
