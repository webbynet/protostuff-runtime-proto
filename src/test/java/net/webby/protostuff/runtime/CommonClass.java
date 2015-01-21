package net.webby.protostuff.runtime;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

import com.dyuproject.protostuff.Tag;

public class CommonClass {

	@Tag(1)
	private String stringValue;

	@Tag(2)
	private Date dateValue;

	@Tag(3)
	private BigInteger biValue;

	@Tag(4)
	private BigDecimal bdValue;
	
	@Tag(5)
	private UUID uuidValue;

}
