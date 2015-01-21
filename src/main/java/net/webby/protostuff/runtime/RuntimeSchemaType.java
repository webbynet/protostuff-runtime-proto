package net.webby.protostuff.runtime;

public enum RuntimeSchemaType {

	ArraySchema,
	ObjectSchema;
	
	public static RuntimeSchemaType findByName(String name) {
		for (RuntimeSchemaType value : values()) {
			if (value.name().equals(name)) {
				return value;
			}
		}
		return null;
	}
	
}
