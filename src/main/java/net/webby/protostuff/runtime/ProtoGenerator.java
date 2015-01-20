package net.webby.protostuff.runtime;


/**
 * Proto File Generator base interface
 * 
 * @author Alex Shvid
 *
 */

public interface ProtoGenerator {

	ProtoGenerator setPackageName(String packageName);
	
	ProtoGenerator setOuterClassName(String outerClassName);
	
	String generate();
	
}
