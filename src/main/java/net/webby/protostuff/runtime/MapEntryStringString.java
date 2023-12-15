package net.webby.protostuff.runtime;

import io.protostuff.Tag;

/**
 * 
 * @author Alex Shvid
 *
 */


public class MapEntryStringString {

	@Tag(Constants.ID_MAP_KEY)
	public String key;
	
	@Tag(Constants.ID_MAP_VALUE)
	public String value;
	
}
