package net.webby.protostuff.runtime;

import com.dyuproject.protostuff.Tag;

/**
 * 
 * @author Alex Shvid
 *
 */

public class UUID {

	@Tag(1)
	public long mostSigBits;
	
	@Tag(2)
	public long leastSigBits;
  
}
