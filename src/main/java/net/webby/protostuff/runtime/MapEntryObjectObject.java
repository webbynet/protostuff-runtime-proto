package net.webby.protostuff.runtime;

import com.dyuproject.protostuff.Tag;

/**
 * 
 * @author Alex Shvid
 *
 */


public final class MapEntryObjectObject {

	@Tag(Constants.ID_MAP_KEY)
	public DynamicObject key;
	
	@Tag(Constants.ID_MAP_VALUE)
	public DynamicObject value;
	
}
