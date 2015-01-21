package net.webby.protostuff.runtime;

import com.dyuproject.protostuff.Tag;

public class WrapperClass {

	@Tag(1)
	protected Boolean booleanValue;

	@Tag(2)
	protected Byte byteValue;

	@Tag(3)
	protected Short shortValue;

	@Tag(4)
	protected Integer intValue;

	@Tag(5)
	protected Long longValue;

	@Tag(6)
	protected Float floatValue;

	@Tag(7)
	protected Double doubleValue;
	
	@Tag(8)
	protected Object objValue;
	
}
