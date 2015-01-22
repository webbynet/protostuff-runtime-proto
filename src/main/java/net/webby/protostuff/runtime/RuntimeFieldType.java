package net.webby.protostuff.runtime;

/**
 * 
 * @author Alex Shvid
 *
 */


public enum RuntimeFieldType {

	RuntimeMessageField,
	RuntimeObjectField,
	RuntimeMapField;
	
	public static RuntimeFieldType findByName(String name) {
		for (RuntimeFieldType value : values()) {
			if (value.name().equals(name)) {
				return value;
			}
		}
		return null;
	}
	
}
