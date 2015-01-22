package net.webby.protostuff.runtime;

import java.util.List;

import com.dyuproject.protostuff.Tag;

public final class MapObjectObject {

	@Tag(Constants.ID_MAP_ENTRY)
	public List<MapEntryObjectObject> value;
	
}
