package com.hometest.enums;

/**
 * @author mosman
 * @File : ChangeMobileStatus.java
 * @Project : HomeTestLabs
 * @Pakage : com.hometest.enums
 * @Date : 30 Nov 2020  16:13:09
 * @Version : 1.0
 */
public enum ChangeMobileType {
	MOBILE("M"), 
	EMAIL("E");

	private String value;

	ChangeMobileType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public static ChangeMobileType getByCode(String code) {
		for (ChangeMobileType type : ChangeMobileType.values())
			if (type.getValue().equalsIgnoreCase(code))
				return type;
		return null;
	}
	@Override
    public String toString() {
        return value;
    }
}
