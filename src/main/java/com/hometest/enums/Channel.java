/**
 * 
 */
package com.hometest.enums;

/**
 * @author mosman
 * @Date  Sep 27, 2020
 * @Channel.java
 * @Pacom.hometest.enums
 * @6:37:32 PM
 */
public enum Channel {
	WEB("web"), 
	ANDROID("and"),
	APPLE("ios"),
	BACKOFFICE("bo");

	private String value;

	Channel(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public static Channel getByCode(String code) {
		for (Channel channel : Channel.values())
			if (channel.getValue().equalsIgnoreCase(code))
				return channel;
		return null;
	}
	
	@Override
    public String toString() {
        return value;
    }
}