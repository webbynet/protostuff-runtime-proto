package net.webby.protostuff.runtime;

import com.dyuproject.protostuff.Tag;

public class PrimitiveClass {

	@Tag(1)
	private boolean booleanValue;

	@Tag(2)
	private byte byteValue;

	@Tag(3)
	private short shortValue;

	@Tag(4)
	private int intValue;

	@Tag(5)
	private long longValue;

	@Tag(6)
	private float floatValue;

	@Tag(7)
	private double doubleValue;


}
