package net.webby.protostuff.runtime;

import io.protostuff.Tag;

/**
 * 
 * @author Alex Shvid
 *
 */

public class EnumObject {

	@Tag(Constants.ID_ENUM)
	public String enumId;
	
	@Tag(Constants.ID_ENUM_VALUE)
	public int ordinal;
	
}
