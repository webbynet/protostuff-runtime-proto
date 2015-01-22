package net.webby.protostuff.runtime;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.dyuproject.protostuff.Tag;

/**
 * 
 * @author Alex Shvid
 *
 */

public class SetClass {

	@Tag(1)
	protected Set<Boolean> booleanValue;

	@Tag(2)
	protected Set<Byte> byteValue;

	@Tag(3)
	protected Set<Character> charValue;
	
	@Tag(4)
	protected Set<Short> shortValue;

	@Tag(5)
	protected Set<Integer> intValue;

	@Tag(6)
	protected Set<Long> longValue;

	@Tag(7)
	protected Set<Float> floatValue;

	@Tag(8)
	protected Set<Double> doubleValue;
	
	@Tag(9)
	protected Set<Object> objValue;
	
	@Tag(10)
	protected Set<String> stringValue;

	@Tag(11)
	protected Set<Date> dateValue;

	@Tag(12)
	protected Set<BigInteger> biValue;

	@Tag(13)
	protected Set<BigDecimal> bdValue;
	
	@Tag(14)
	protected Set<UUID> uuidValue;
	
}
