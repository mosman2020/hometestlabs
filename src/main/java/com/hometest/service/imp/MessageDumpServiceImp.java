/**
 * 
 */
package com.hometest.service.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hometest.mybatis.dao.MessageDumpDao;
import com.hometest.mybatis.domain.MessageDump;
import com.hometest.service.MessageDumpService;
import com.hometest.utils.payload.request.Request;
import com.hometest.utils.payload.response.Response;


/**
 * @author moosman
 *
 */

@Service

public class MessageDumpServiceImp implements MessageDumpService {

	private Logger logger = LoggerFactory.getLogger(MessageDumpServiceImp.class);

	@Autowired
	MessageDumpDao messageDupmDao;

	@Override
	@Async
	public void insertMessageDump(Response response) {
		messageDupmDao.insertMessageDump(MessageDump.builder().backendRequestId(response.getHeader().getBackendRequestId()).build());
	}

	@Override
	@Async
	public void insertMessageDump(Request request) {
		messageDupmDao.insertMessageDump(MessageDump.builder().backendRequestId(request.getHeader().getBackendRequestId()).build());
	}

	@Override
	@Async
	public void insertMessageDump(MessageDump message) {
		try {
			messageDupmDao.insertMessageDump(message);
		}catch(Exception ex) {
			logger.error(ex.getMessage());
			logger.error(ex.getCause().getMessage());
		}
	}

}
