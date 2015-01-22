package net.webby.protostuff.runtime;

/**
 * 
 * @author Alex Shvid
 *
 */

public enum RuntimeSchemaType {

	ArraySchema,
	ObjectSchema,
	MapSchema;
	
	public static RuntimeSchemaType findByName(String name) {
		for (RuntimeSchemaType value : values()) {
			if (value.name().equals(name)) {
				return value;
			}
		}
		return null;
	}
	
}
