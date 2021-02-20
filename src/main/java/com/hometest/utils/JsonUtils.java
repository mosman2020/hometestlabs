/**
 * 
 */
package com.hometest.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hometest.utils.payload.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author moosman
 *
 */
public class JsonUtils {

	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	public static <T> T convertRequestToObject(Request request, Class<T> type) {
//		T returnValue = null;
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
		return objectMapper.convertValue(request.getBody(), type);
	}

	public static JsonNode convertObjectToJsonNode(Object obj) {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.convertValue(obj, JsonNode.class);
	}

	public static <T> T convertJsonToObject(String json, Class<T> type) {
		T returnValue = null;
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
		try {
			returnValue = objectMapper.readValue(json, type);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return returnValue;
	}
/*
	public static ProcessingReport validateRequestSchema(String req) {
		ProcessingReport report=null;
		try {
			final JsonNode requestSchema = JsonLoader.fromResource("/schemas/request-schema.json");
			final JsonNode request = convertObjectToJsonNode(req);
			final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
			final JsonSchema schema = factory.getJsonSchema(requestSchema);

			report = schema.validate(request);
			logger.info("==================================Report Start======================================");
			logger.info("report : " + report);
			logger.info("--------------------------------------------");
			report.forEach(porcessingMessage -> {
				logger.info("porcessingMessage {} : "+porcessingMessage);  
				logger.info("porcessingMessage.asJson {} : "+porcessingMessage.asJson().at("/instance/pointer").asText());
				logger.info("porcessingMessage.asException {} : "+porcessingMessage.asException());
				logger.info("porcessingMessage.asException().getShortMessage() {} : "+porcessingMessage.asException().getShortMessage());
			});
			logger.info("==================================Report Ends======================================");
		} catch (IOException | ProcessingException e) {
			e.printStackTrace();
		}
		return report;
	}
	
	public static ProcessingReport validateResponseSchema(Response res) {
		ProcessingReport report=null;
		try {
			final JsonNode responseSchema = JsonLoader.fromResource("/schemas/response-schema.json");
			final JsonNode response = convertObjectToJsonNode(res);
			final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
			final JsonSchema schema = factory.getJsonSchema(responseSchema);

			report = schema.validate(response);
			logger.info("==================================Report Start2======================================");
//			logger.info("report : " + report);
			report.forEach(porcessingMessage -> {
				logger.info("porcessingMessage {} : "+porcessingMessage);
				logger.info("porcessingMessage.asJson {} : "+porcessingMessage.asJson());
				logger.info("porcessingMessage.asException {} : "+porcessingMessage.asException());
				logger.info("porcessingMessage.asException().getShortMessage() {} : "+porcessingMessage.asException().getShortMessage());
			});
			
			logger.info("==================================Report Ends2======================================");
//			System.out.println(report);
		} catch (IOException | ProcessingException e) {
			e.printStackTrace();
		}
		return report;
	}
*/
}
