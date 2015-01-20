package net.webby.protostuff.runtime;

import com.dyuproject.protostuff.Schema;

/**
 * Proto File Generator base interface
 * 
 * @author Alex Shvid
 *
 */

public interface ProtoGenerator {

	void generate(Schema<?> schema, String outerClassname, StringBuilder output);
	
}
