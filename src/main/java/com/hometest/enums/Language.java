/**
 * 
 */
package com.hometest.enums;

/**
 * @author moosman
 *
 */
public enum Language {
	ARABIC("Ar"), 
	ENGLISH("En");

	private String value;

	Language(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public static Language getByCode(String code) {
		for (Language lang : Language.values())
			if (lang.getValue().equalsIgnoreCase(code))
				return lang;
		return null;
	}
	@Override
    public String toString() {
        return value;
    }
}
