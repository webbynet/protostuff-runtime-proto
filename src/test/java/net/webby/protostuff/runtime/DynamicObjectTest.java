package net.webby.protostuff.runtime;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.dyuproject.protostuff.ByteString;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * 
 * @author Alex Shvid
 *
 */


public class DynamicObjectTest {
	
	private Schema<ObjectClass> objectSchema = RuntimeSchema.getSchema(ObjectClass.class);
	private Schema<DynamicObjectClass> dynamicObjectSchema = RuntimeSchema.getSchema(DynamicObjectClass.class);
	private LinkedBuffer buffer = LinkedBuffer.allocate(4096);
	
	@Test
	public void testPrint() throws Exception {
		
		Schema<DynamicObject> schema = RuntimeSchema.getSchema(DynamicObject.class);
		
		String content = Generators.newProtoGenerator(schema).generate();
		
		System.out.println(content);
	}
	
	@Test
	public void testBoolean() throws Exception {
		testField(Boolean.TRUE, "booleanValue");
	}
	
	@Test
	public void testByte() throws Exception {
		testField(Byte.valueOf((byte)55), "byteValue");
	}
	
	@Test
	public void testChar() throws Exception {
		testField(Character.valueOf('a'), "charValue");
	}

	@Test
	public void testShort() throws Exception {
		testField(Short.valueOf((short)555), "shortValue");
	}
	
	@Test
	public void testInt() throws Exception {
		testField(Integer.valueOf(5555), "intValue");
	}
	
	@Test
	public void testLong() throws Exception {
		testField(Long.valueOf(55555), "longValue");
	}
	
	@Test
	public void testFloat() throws Exception {
		testField(Float.valueOf(0.77f), "floatValue");
	}
	
	@Test
	public void testDouble() throws Exception {
		testField(Double.valueOf(0.888), "doubleValue");
	}
	
	@Test
	public void testString() throws Exception {
		testField("str", "stringValue");
	}

	@Test
	public void testBytes() throws Exception {
		testField(ByteString.copyFrom(new byte[] { 0x45, 0x23 }), "bytesValue");
	}

	@Test
	public void testByteArray() throws Exception {
		testField(new byte[] { 0x45, 0x23 }, "byteArrayValue");
	}
	
	@Test
	public void testBigDecimal() throws Exception {
		testField(new BigDecimal("10.123"), "bigDecimalValue");
	}
	
	@Test
	public void testBigInteger() throws Exception {
		testField(BigInteger.valueOf(12345), "bigIntegerValue");
	}
	
	@Test
	public void testDate() throws Exception {
		testField(new Date(), "dateValue");
	}

	@Test
	public void testArray() throws Exception {
		testField(new Integer[] { new Integer(555), new Integer(777) }, "arrayClass", Integer.class.getName());
	}

	@Test
	public void testEnum() throws Exception {
		testField(SimpleEnum.VALUE1, "enumClass", SimpleEnum.class.getName());
	}

	@Test
	public void testObject() throws Exception {
		testField(new Object(), "objectValue", new JavaObject());
	}
	
	private void testField(Object expected, String fieldName) throws Exception {
		testField(expected, fieldName, expected);
	}
	
	private void testField(Object expected, String fieldName, Object expectedObject) throws Exception {
		
		ObjectClass ins = new ObjectClass();
		ins.value = expected;
		byte[] blob = ProtobufIOUtil.toByteArray(ins, objectSchema, buffer);
		
		DynamicObjectClass message = dynamicObjectSchema.newMessage();
		ProtobufIOUtil.mergeFrom(blob, message, dynamicObjectSchema);
		
		Field field = DynamicObject.class.getDeclaredField(fieldName);

		Object actual = field.get(message.value);
		
		if (expectedObject.getClass().isArray()) {
			int len = Array.getLength(expectedObject);
			Assert.assertEquals(len, Array.getLength(actual));
			for (int i = 0; i != len; ++i) {
				Object e = Array.get(expectedObject, i);
				Object a = Array.get(actual, i);
				Assert.assertEquals(e, a);
			}
		}
		else {
			Assert.assertEquals(expectedObject, actual);
		}
		
	}
	
}
