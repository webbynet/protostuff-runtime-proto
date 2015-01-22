package net.webby.protostuff.runtime;

import java.util.List;

import com.dyuproject.protostuff.Tag;

/**
 * 
 * @author Alex Shvid
 *
 */


public final class MapStringString {

	@Tag(Constants.ID_MAP_ENTRY)
	public List<MapEntryStringString> value;
	
}
