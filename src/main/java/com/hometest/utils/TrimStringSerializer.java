/**
 * 
 */
package com.hometest.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author moosman
 *
 */
//@JsonComponent
public class TrimStringSerializer extends JsonSerializer<String> {

	private Logger logger = LoggerFactory.getLogger(TrimStringSerializer.class);

	public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//		gen.writeStartObject();
		logger.info("-------------------From TrimStringSerializer --------------");
		gen.writeString(value != null ? value.trim() : null);
//		gen.writeEndObject();
	}

}
