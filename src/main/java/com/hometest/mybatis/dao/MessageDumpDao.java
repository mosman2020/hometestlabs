/**
 * 
 */
package com.hometest.mybatis.dao;

import com.hometest.mybatis.domain.MessageDump;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author moosman
 *
 */
@Component
public class MessageDumpDao  {
	
	private final SqlSession sqlSession;

	private Logger logger = LoggerFactory.getLogger(MessageDumpDao.class);

	public MessageDumpDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public int insertMessageDump(MessageDump messageDump) {
		logger.info("Insert Message Dump : "+messageDump);
		return this.sqlSession.insert("insertMessageDump", messageDump) ;
	}
	
	
}
