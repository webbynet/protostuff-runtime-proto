package net.webby.protostuff.runtime;

import com.dyuproject.protostuff.Schema;

/**
 * Collection of proto file generators based on different schema types of the Protostuff
 * 
 * @author Alex Shvid
 *
 */

public final class Generators {

	public static RuntimeProtoGenerator newRuntimeProtoGenerator(Schema<?> schema) {
		return new RuntimeProtoGenerator(schema);
	}
	
}
