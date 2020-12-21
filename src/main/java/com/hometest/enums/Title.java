/**
 * 
 */
package com.hometest.enums;

/**
 * @author mosman
 * @Date  Oct 2, 2020
 * @Title.java
 * @Pacom.hometest.enums
 * @10:56:57 AM
 */
public enum Title {
	MR("Mr"), 
	MRS("Mrs"),
	DR("Dr"),
	ENG("Eng");

	private String value;

	Title(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public static Title getByCode(String code) {
		for (Title title : Title.values())
			if (title.getValue().equalsIgnoreCase(code))
				return title;
		return null;
	}
	
	@Override
    public String toString() {
        return value;
    }
}