/**
 * 
 */
package com.hometest.enums;

/**
 * @author moosman
 *
 */
public enum UserStatus {
	
	ACTIVE("A"), 
	CREATED("C"),
	DISABLED("D"),
	LOCKED("L");

	private String value;

	UserStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public static UserStatus getByCode(String code) {
		for (UserStatus status : UserStatus.values())
			if (status.getValue().equalsIgnoreCase(code))
				return status;
		return null;
	}
	@Override
    public String toString() {
        return value;
    }
}
