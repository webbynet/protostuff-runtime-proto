package net.webby.protostuff.runtime;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class DynamicTest {
	
	private Schema<ObjectClass> objectSchema = RuntimeSchema.getSchema(ObjectClass.class);
	private Schema<DynamicObjectClass> dynamicObjectSchema = RuntimeSchema.getSchema(DynamicObjectClass.class);
	private LinkedBuffer buffer = LinkedBuffer.allocate(4096);
	
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
	
	private void testField(Object expected, String fieldName) throws Exception {
		
		ObjectClass ins = new ObjectClass();
		ins.value = expected;
		byte[] blob = ProtobufIOUtil.toByteArray(ins, objectSchema, buffer);
		
		DynamicObjectClass message = dynamicObjectSchema.newMessage();
		ProtobufIOUtil.mergeFrom(blob, message, dynamicObjectSchema);
		
		Field field = DynamicObject.class.getDeclaredField(fieldName);
		
		Assert.assertEquals(expected, field.get(message.value));
		
	}
	
}
