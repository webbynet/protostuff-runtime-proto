package net.webby.protostuff.runtime;


/**
 * 
 * @author Alex Shvid
 *
 */


public enum StringEnum {

	VALUE1("STR1"), VALUE2("STR2");
	
	private final String stringValue;
	
	private StringEnum(String value) {
		this.stringValue = value;
	}

	public String getStringValue() {
		return stringValue;
	}

}
