package net.webby.protostuff.runtime;

/**
 * Collection of proto file generators based on different schema types of the Protostuff
 * 
 * @author Alex Shvid
 *
 */

public final class Generators {

	public static RuntimeProtoGenerator getRuntimeProtoGenerator() {
		return new RuntimeProtoGenerator();
	}
	
}
