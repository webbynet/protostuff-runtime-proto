package net.webby.protostuff.runtime;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

import com.dyuproject.protostuff.Tag;

public class CommonClass {

	@Tag(1)
	protected String stringValue;

	@Tag(2)
	protected Date dateValue;

	@Tag(3)
	protected BigInteger biValue;

	@Tag(4)
	protected BigDecimal bdValue;
	
	@Tag(5)
	protected UUID uuidValue;

}
