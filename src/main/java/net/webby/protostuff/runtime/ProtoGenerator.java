package net.webby.protostuff.runtime;


/**
 * Proto File Generator base interface
 * 
 * @author Alex Shvid
 *
 */

public interface ProtoGenerator {

	ProtoGenerator setPackageName(String packageName);
	
	ProtoGenerator setJavaPackageName(String javaPackageName);
	
	ProtoGenerator setJavaOuterClassName(String outerClassName);
	
	String generate();
	
}
