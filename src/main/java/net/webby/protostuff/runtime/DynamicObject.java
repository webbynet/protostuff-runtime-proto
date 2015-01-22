package net.webby.protostuff.runtime;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.dyuproject.protostuff.Tag;

public final class DynamicObject {

	@Tag(Constants.ID_BOOL)
	public boolean booleanValue;
	
	@Tag(Constants.ID_BYTE)
	public byte byteValue;

	@Tag(Constants.ID_CHAR)
	public char charValue;

	@Tag(Constants.ID_SHORT)
	public short shortValue;

	@Tag(Constants.ID_INT32)
	public int intValue;

	@Tag(Constants.ID_INT64)
	public long longValue;

	@Tag(Constants.ID_FLOAT)
	public float floatValue;

	@Tag(Constants.ID_DOUBLE)
	public double doubleValue;
	
	@Tag(Constants.ID_STRING)
	public String stringValue;
	
	@Tag(Constants.ID_BYTES)
	public byte[] bytesValue;

	@Tag(Constants.ID_BYTE_ARRAY)
	public byte[] byteArrayValue;

	@Tag(Constants.ID_BIGDECIMAL)
	public BigDecimal bigDecimalValue;

	@Tag(Constants.ID_BIGINTEGER)
	public BigInteger bigIntegerValue;

	@Tag(Constants.ID_DATE)
	public Date dateValue;
	
	@Tag(Constants.ID_ARRAY)
	public String arrayValue;
}
