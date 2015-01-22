package net.webby.protostuff.runtime;

import com.dyuproject.protostuff.Tag;

/**
 * 
 * @author Alex Shvid
 *
 */


public class MapEntryStringObject {

	@Tag(Constants.ID_MAP_KEY)
	public String key;
	
	@Tag(Constants.ID_MAP_VALUE)
	public DynamicObject value;
	
}
