package net.webby.protostuff.runtime;

import com.dyuproject.protostuff.Tag;

public class MapEntryStringString {

	@Tag(Constants.ID_MAP_KEY)
	public String key;
	
	@Tag(Constants.ID_MAP_VALUE)
	public String value;
	
}
