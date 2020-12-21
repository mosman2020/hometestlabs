/**
 * 
 */
package com.hometest.service;

import com.hometest.mybatis.domain.MessageDump;
import com.hometest.utils.payload.request.Request;
import com.hometest.utils.payload.response.Response;

/**
 * @author moosman
 *
 */
public interface MessageDumpService {

	void insertMessageDump(Request request);
	void insertMessageDump(MessageDump message);
	void insertMessageDump(Response response);

}
