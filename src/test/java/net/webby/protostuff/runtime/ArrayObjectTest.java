package net.webby.protostuff.runtime;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.Tag;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class ArrayObjectTest {

	private Schema<ArrayObjectClass> arrayObjectSchema = RuntimeSchema.getSchema(ArrayObjectClass.class);
	private LinkedBuffer buffer = LinkedBuffer.allocate(4096);
	
	@Test
	public void testPrint() throws Exception {
		
		Schema<ArrayObject> schema = RuntimeSchema.getSchema(ArrayObject.class);
		
		String content = Generators.newProtoGenerator(schema).generate();
		
		System.out.println(content);
		
	}
	
  /*
   * BOOL
   */
	
	public static class PrimitiveBooleanArrayObjectClass {
		
		@Tag(1)
		protected boolean[] value;
	}
	
	@Test
	public void testPrimitiveBoolean() throws Exception {
		testClass(PrimitiveBooleanArrayObjectClass.class, new boolean[] { false, true }, boolean.class, "booleanValue"); 
	}
	
	public static class BooleanArrayObjectClass {
		
		@Tag(1)
		protected Boolean[] value;
	}
	
	@Test
	public void testBoolean() throws Exception {
		testClass(BooleanArrayObjectClass.class, new Boolean[] { Boolean.FALSE, Boolean.TRUE }, Boolean.class, "booleanValue"); 
	}

	/*
	 * BYTE
	 */
	
	public static class ByteArrayObjectClass {
		
		@Tag(1)
		protected Byte[] value;
	}
	
	@Test
	public void testByte() throws Exception {
		testClass(ByteArrayObjectClass.class, new Byte[] { 0x12, 0x34 }, Byte.class, "byteValue"); 
	}
	
	/*
	 * CHAR
	 */
	
	public static class PrimitiveCharArrayObjectClass {
		
		@Tag(1)
		protected char[] value;
	}
	
	@Test
	public void testPrimitiveChar() throws Exception {
		testClass(PrimitiveCharArrayObjectClass.class, new char[] { 'a', 'b' }, char.class, "charValue"); 
	}
	
	public static class CharArrayObjectClass {
		
		@Tag(1)
		protected Character[] value;
	}
	
	@Test
	public void testChar() throws Exception {
		testClass(CharArrayObjectClass.class, new Character[] { 'a', 'b' }, Character.class, "charValue"); 
	}
	
	/*
	 * SHORT
	 */
	
	public static class PrimitiveShortArrayObjectClass {
		
		@Tag(1)
		protected short[] value;
	}
	
	@Test
	public void testPrimitiveShort() throws Exception {
		testClass(PrimitiveShortArrayObjectClass.class, new short[] { 555, 777 }, short.class, "shortValue"); 
	}
	
	public static class ShortArrayObjectClass {
		
		@Tag(1)
		protected Short[] value;
	}
	
	@Test
	public void testShort() throws Exception {
		testClass(ShortArrayObjectClass.class, new Short[] { 555, 777 }, Short.class, "shortValue"); 
	}
	
	/*
	 * INT
	 */
	
	public static class PrimitiveIntArrayObjectClass {
		
		@Tag(1)
		protected int[] value;
	}
	
	@Test
	public void testPrimitiveInt() throws Exception {
		testClass(PrimitiveIntArrayObjectClass.class, new int[] { 555, 777 }, int.class, "intValue"); 
	}
	
	public static class IntArrayObjectClass {
		
		@Tag(1)
		protected Integer[] value;
	}
	
	@Test
	public void testInt() throws Exception {
		testClass(IntArrayObjectClass.class, new Integer[] { 555, 777 }, Integer.class, "intValue"); 
	}
	
	/*
	 * LONG
	 */
	
	public static class PrimitiveLongArrayObjectClass {
		
		@Tag(1)
		protected long[] value;
	}
	
	@Test
	public void testPrimitiveLong() throws Exception {
		testClass(PrimitiveLongArrayObjectClass.class, new long[] { 555l, 777l }, long.class, "longValue"); 
	}
	
	public static class LongArrayObjectClass {
		
		@Tag(1)
		protected Long[] value;
	}
	
	@Test
	public void testLong() throws Exception {
		testClass(LongArrayObjectClass.class, new Long[] { 555l, 777l }, Long.class, "longValue"); 
	}
	
	/*
	 * FLOAT
	 */
	
	public static class PrimitiveFloatArrayObjectClass {
		
		@Tag(1)
		protected float[] value;
	}
	
	@Test
	public void testPrimitiveFloat() throws Exception {
		testClass(PrimitiveFloatArrayObjectClass.class, new float[] { 0.55f, 0.77f }, float.class, "floatValue"); 
	}
	
	public static class FloatArrayObjectClass {
		
		@Tag(1)
		protected Float[] value;
	}
	
	@Test
	public void testFloat() throws Exception {
		testClass(FloatArrayObjectClass.class, new Float[] { 0.55f, 0.77f }, Float.class, "floatValue"); 
	}	
	
	/*
	 * DOUBLE
	 */
	
	public static class PrimitiveDoubleArrayObjectClass {
		
		@Tag(1)
		protected double[] value;
	}
	
	@Test
	public void testPrimitiveDouble() throws Exception {
		testClass(PrimitiveDoubleArrayObjectClass.class, new double[] { 0.55, 0.77 }, double.class, "doubleValue"); 
	}
	
	public static class DoubleArrayObjectClass {
		
		@Tag(1)
		protected Double[] value;
	}
	
	@Test
	public void testDouble() throws Exception {
		testClass(DoubleArrayObjectClass.class, new Double[] { 0.55, 0.77 }, Double.class, "doubleValue"); 
	}	
	
	/*
	 * IMPL
	 */
	
	private <T> void testClass(Class<T> schemaCls, Object expectedArray, Class<?> itemClass, String fieldName) throws Exception {
		
		int len = Array.getLength(expectedArray);
		
		Schema<T> schema = RuntimeSchema.getSchema(schemaCls);
		T ins = schemaCls.newInstance();
		schemaCls.getDeclaredField("value").set(ins, expectedArray);
		
		byte[] blob = ProtobufIOUtil.toByteArray(ins, schema, buffer);
		
		ArrayObjectClass message = arrayObjectSchema.newMessage();
		ProtobufIOUtil.mergeFrom(blob, message, arrayObjectSchema);
		
		Assert.assertEquals(itemClass.getName(), message.value.id);
		Assert.assertNull(message.value.mapped);
		Assert.assertEquals(len, message.value.len);
		Assert.assertEquals(1, message.value.dimension);
		
		List<DynamicObject> list = message.value.value;
		Assert.assertEquals(len, list.size());
		
		Field field = DynamicObject.class.getDeclaredField(fieldName);
		
		for (int i = 0; i != len; ++i) {
			Object actual = field.get(list.get(i));
			Assert.assertEquals(Array.get(expectedArray, i), actual);
		}
		
	}
}
