/**
 * 
 */
package com.hometest.service;

/**
 * @author moosman
 *
 */
public interface MessageService {
	 public String getMessage(String id, Object[] params,String lang);
	 public String getMessage(String id, String lang);
}
