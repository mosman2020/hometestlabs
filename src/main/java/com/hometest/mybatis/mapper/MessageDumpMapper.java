/**
 * 
 */
package com.hometest.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.hometest.mybatis.domain.MessageDump;

/**
 * @author moosman
 *
 */
@Mapper
public interface MessageDumpMapper {

	void insertMsgDump(MessageDump msgDump);

}
