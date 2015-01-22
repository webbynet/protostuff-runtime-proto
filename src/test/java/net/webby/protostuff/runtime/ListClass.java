package net.webby.protostuff.runtime;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.dyuproject.protostuff.Tag;

public class ListClass {

	@Tag(1)
	protected List<Boolean> booleanValue;

	@Tag(2)
	protected List<Byte> byteValue;

	@Tag(3)
	protected List<Character> charValue;
	
	@Tag(4)
	protected List<Short> shortValue;

	@Tag(5)
	protected List<Integer> intValue;

	@Tag(6)
	protected List<Long> longValue;

	@Tag(7)
	protected List<Float> floatValue;

	@Tag(8)
	protected List<Double> doubleValue;
	
	@Tag(9)
	protected List<Object> objValue;
	
	@Tag(10)
	protected List<String> stringValue;

	@Tag(11)
	protected List<Date> dateValue;

	@Tag(12)
	protected List<BigInteger> biValue;

	@Tag(13)
	protected List<BigDecimal> bdValue;
	
	@Tag(14)
	protected List<UUID> uuidValue;

}
