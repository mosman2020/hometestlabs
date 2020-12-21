/**
 * 
 */
package com.hometest.enums;

/**
 * @author mosman
 * @Date  Sep 28, 2020
 * @UserType.java
 * @Pacom.hometest.enums
 * @3:29:11 PM
 */
public enum UserType {
	
	INDIVIDUAL("INDV"), 
	DOCTORE("DOCR"),
	LABORATORIES("LAB"),
	CLINIC("CLNC");

	private String value;

	UserType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public static UserType getByCode(String code) {
		for (UserType usertype : UserType.values())
			if (usertype.getValue().equalsIgnoreCase(code))
				return usertype;
		return null;
	}
	@Override
    public String toString() {
        return value;
    }
}
