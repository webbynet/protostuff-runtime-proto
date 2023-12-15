package net.webby.protostuff.runtime;

import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * Collection of .proto file generators
 * 
 * @author Alex Shvid
 *
 */

public final class Generators {

	public static ProtoGenerator newProtoGenerator(Schema<?> schema) {
		if (schema instanceof RuntimeSchema) {
			return new RuntimeProtoGenerator(schema);
		}
		throw new IllegalArgumentException("unsupported schema type " + schema.getClass());
	}
	
}
