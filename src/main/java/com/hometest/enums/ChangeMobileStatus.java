package com.hometest.enums;

/**
 * @author mosman
 * @File : ChangeMobileStatus.java
 * @Project : HomeTestLabs
 * @Pakage : com.hometest.enums
 * @Date : 30 Nov 2020  16:13:09
 * @Version : 1.0
 */
public enum ChangeMobileStatus {
	PENDING("P"), 
	COMPLETED("C");

	private String value;

	ChangeMobileStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public static ChangeMobileStatus getByCode(String code) {
		for (ChangeMobileStatus status : ChangeMobileStatus.values())
			if (status.getValue().equalsIgnoreCase(code))
				return status;
		return null;
	}
	@Override
    public String toString() {
        return value;
    }
}
