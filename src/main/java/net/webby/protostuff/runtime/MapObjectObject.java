package net.webby.protostuff.runtime;

import java.util.List;

import io.protostuff.Tag;

/**
 * 
 * @author Alex Shvid
 *
 */


public final class MapObjectObject {

	@Tag(Constants.ID_MAP_ENTRY)
	public List<MapEntryObjectObject> value;
	
}
