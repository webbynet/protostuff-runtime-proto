package net.webby.protostuff.runtime;

import com.dyuproject.protostuff.Tag;

public class WrapperClass {

	@Tag(1)
	protected Boolean booleanValue;

	@Tag(2)
	protected Byte byteValue;

	@Tag(3)
	protected Character charValue;
	
	@Tag(4)
	protected Short shortValue;

	@Tag(5)
	protected Integer intValue;

	@Tag(6)
	protected Long longValue;

	@Tag(7)
	protected Float floatValue;

	@Tag(8)
	protected Double doubleValue;
	
	@Tag(9)
	protected Object objValue;
	
}
