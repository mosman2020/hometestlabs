/**
 * 
 */
package com.hometest.utils;

/**
 * @author mosman
 * @Date  Sep 25, 2020
 * @ErrorCodes.java
 * @Pacom.hometest.utils
 * @10:06:42 AM
 */
/*	
 
	SUCCESS OPERATION      		000000
	Internal System Error 		E999999

1-General Errors 				E100000
	1- Invalid JSON Object 		E100001
	2- Access Denied			E100002
	3- Service Not Available	E100003
	
2- Validation Errors 			E200000
	1- mandatory field 			E200001
	2- invalid field format		E200002
	3- invalid mail				E200003
	4- invalid date format		E200004
	5-							E200005
	6-							E200006
	
3- Business Errors							E300000
	\\ Service 1 Ex: User Management		E300100		
	1- Invalid OTP							E300101
	2- User Already Exists					E300102
	3- Invalid username and\or password		E300103 
	4-
	5-
	99-										E300199
	
	\\ Service 2 Ex: Order Management		E300200		
	1-										E300201
	2-										E300202
	3-										E300203
	4-
	99-										E300299
	
	\\Service 9								E300900
	1-										E300901
	2-										E300902
	3-										E300903
	4-
	99-										E300999
	
	\\Service 10							E301000
	1-										E301001
	2-										E301002
	3-										E301003
	4-
	99-										E301099
	
*/
public class ErrorCodes {
	
	// SUCCESS OPERATION
	public static final String SUCESS_OPERATION = "000000";
	// INTERNAL SYSTEM ERROR
	public static final String INTERNAL_SYSTEM_ERROR = "E999999";
	
	
	//CATEGORY-1 GENERIC FAILURE 
	public static final String GENERIC_FAILURE_ERROR = "E100000";
		public static final String INVALID_JSON_OBJECT = "E100001";
		public static final String ACCESS_DENIED = "E100002";
		public static final String NOT_FOUND = "E100003";
		public static final String INSUFFICIENT_PRIVILEGES = "E100004";
	
	//CATEGORY-2 VALIDATION ERRORS
	public static final String VALIDATION_ERROR = "E200000";
		public static final String MANDATORY_FIELD = "E200001";
		public static final String INVALID_FIELD_FORMAT = "E200002";
		public static final String INVALID_MAIL = "E200003";
		public static final String INVALID_DATE = "E200004";
		public static final String INVALID_Enum = "E200005";
		public static final String INVALID_RANGE = "E200006";
		public static final String INVALID_SIZE = "E200007";
		public static final String INVALID_MOBILE = "E200008";
//		public static final String MISMATCH_PASSWORDS = "E200009";
	
	//CATEGORY-3 BUSINESS ERRORS
	public static final String BUSINESS_ERROR = "E300000";
		// LIST OF USER MANAGEMENT SERVICE ERRORS (E300100)
		public static final String INVALID_USER_PASSWORD = "E300101";
		public static final String USER_EXISTS = "E300102";
		public static final String USER_NOT_VERIFIED = "E300103";
		public static final String USER_INACTIVE = "E300104";
		public static final String USER_DELETED = "E300105";
		public static final String USER_LOCKED = "E300106";
		public static final String USER_PASS_EXPIRED = "E300107";
		public static final String OLD_NEW_PASSWORD_MATCHED = "E300108";
		public static final String USER_NOT_EXISTS = "E300109";
		public static final String NO_DATA_FOUND = "E30110";
		
		// LIST OF ORDER MANAGEMENT SERVICE ERRORS (E300200)
		public static final String NON_PAID_ORDER = "E300201";
		public static final String PAYMENT_FAILED = "E300202";
	
	
	
	
}
