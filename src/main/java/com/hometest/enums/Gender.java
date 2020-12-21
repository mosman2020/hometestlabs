/**
 * 
 */
package com.hometest.enums;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author moosman
 *
 */
public enum Gender{

	MALE("M"), 
	FEMALE("F");

	private String value;

	Gender(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public static Gender getByCode(String code) {
		for (Gender gndr : Gender.values())
			if (gndr.getValue().equalsIgnoreCase(code))
				return gndr;
		return null;
	}
	@Override
    public String toString() {
        return value;
    }
	
/*
	@JsonProperty("M")
	Male,
	@JsonProperty("F")
    Female,
    @JsonProperty("O")
    Other;

private String gender ; 
	
    private final static Set<String> values = new HashSet<String>(Gender.values().length);

    public String getValue() {
        return gender;
    }
    static{
        for(Gender f: Gender.values())
            values.add(f.name());
    }

    public static boolean contains(String value){
        return values.contains(value);
    }
*/  
}
