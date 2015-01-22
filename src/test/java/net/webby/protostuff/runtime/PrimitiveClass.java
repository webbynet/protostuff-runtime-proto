package net.webby.protostuff.runtime;

import com.dyuproject.protostuff.Tag;

/**
 * 
 * @author Alex Shvid
 *
 */


public class PrimitiveClass {

	@Tag(1)
	protected boolean booleanValue;

	@Tag(2)
	protected byte byteValue;

	@Tag(3)
	protected char charValue;
	
	@Tag(4)
	protected short shortValue;

	@Tag(5)
	protected int intValue;

	@Tag(6)
	protected long longValue;

	@Tag(7)
	protected float floatValue;

	@Tag(8)
	protected double doubleValue;


}
