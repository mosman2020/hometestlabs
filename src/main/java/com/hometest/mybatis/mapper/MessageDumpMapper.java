/**
 * 
 */
package com.hometest.mybatis.mapper;

import com.hometest.mybatis.domain.MessageDump;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author moosman
 *
 */
@Mapper
public interface MessageDumpMapper {

	void insertMsgDump(MessageDump msgDump);

}
