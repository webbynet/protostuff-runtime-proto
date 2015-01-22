package net.webby.protostuff.runtime;

import com.dyuproject.protostuff.Tag;

/**
 * 
 * @author Alex Shvid
 *
 */


public class MapObjectClass {

	@Tag(1)
	protected MapStringString stringToString;

	@Tag(2)
	protected MapStringObject stringToObject;

	@Tag(3)
	protected MapObjectObject objectToObject;
	
}
