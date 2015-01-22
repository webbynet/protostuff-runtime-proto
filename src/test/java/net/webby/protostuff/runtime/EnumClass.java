package net.webby.protostuff.runtime;

import com.dyuproject.protostuff.Tag;

/**
 * 
 * @author Alex Shvid
 *
 */

public class EnumClass {

	@Tag(1)
	protected SimpleEnum simpleEnum;

	@Tag(2)
	protected StringEnum stringEnum;
	
}
