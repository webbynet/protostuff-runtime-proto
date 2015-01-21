package net.webby.protostuff.runtime;

import com.dyuproject.protostuff.Tag;

public class PrimitiveClass {

	@Tag(1)
	protected boolean booleanValue;

	@Tag(2)
	protected byte byteValue;

	@Tag(3)
	protected short shortValue;

	@Tag(4)
	protected int intValue;

	@Tag(5)
	protected long longValue;

	@Tag(6)
	protected float floatValue;

	@Tag(7)
	protected double doubleValue;


}
