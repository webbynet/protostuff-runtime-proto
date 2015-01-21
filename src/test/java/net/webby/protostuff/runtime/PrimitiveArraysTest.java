package net.webby.protostuff.runtime;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.dyuproject.protostuff.ByteArrayInput;
import com.dyuproject.protostuff.Input;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.parser.Field;
import com.dyuproject.protostuff.parser.Message;
import com.dyuproject.protostuff.parser.Proto;
import com.dyuproject.protostuff.parser.ProtoUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class PrimitiveArraysTest {

	private Schema<PrimitiveArraysClass> schema = RuntimeSchema.getSchema(PrimitiveArraysClass.class);
	private LinkedBuffer buffer = LinkedBuffer.allocate(4096);
	
	@Test
	public void test() throws Exception {
		
		String content = Generators.newProtoGenerator(schema).generate();
		
		System.out.println(content);
		
		Proto proto = new Proto(new File("test.proto"));
		ProtoUtil.loadFrom(new ByteArrayInputStream(content.getBytes()), proto);
		 
		String packageName = this.getClass().getPackage().getName();
		
		Assert.assertEquals(proto.getPackageName(), packageName.replace('.', '_'));
		Assert.assertEquals(proto.getJavaPackageName(), packageName);
		Assert.assertEquals(0, proto.getEnumGroups().size());
		Assert.assertEquals(1, proto.getMessages().size());
		
		Message primitiveMsg = proto.getMessage("PrimitiveArraysClass");
		Assert.assertNotNull(primitiveMsg);
		
		Assert.assertEquals(7,  primitiveMsg.getFieldCount());
		Assert.assertEquals("ArrayObject", primitiveMsg.getField("booleanValue").getJavaType());
		Assert.assertTrue(primitiveMsg.getField("byteValue") instanceof Field.Bytes);
		Assert.assertEquals("ArrayObject", primitiveMsg.getField("shortValue").getJavaType());
		Assert.assertEquals("ArrayObject", primitiveMsg.getField("intValue").getJavaType());
		Assert.assertEquals("ArrayObject", primitiveMsg.getField("longValue").getJavaType());
		Assert.assertEquals("ArrayObject", primitiveMsg.getField("floatValue").getJavaType());
		Assert.assertEquals("ArrayObject", primitiveMsg.getField("doubleValue").getJavaType());
		
	}
	
	@Test
	public void testIO() throws Exception {
		
		PrimitiveArraysClass instance = new PrimitiveArraysClass();
		
		instance.booleanValue = new boolean[1];

		byte[] blob = ProtobufIOUtil.toByteArray(instance, schema, buffer);
		
		Input in = new ByteArrayInput(blob, false);

		int fieldNum = in.readFieldNumber(null); // PrimitiveArraysClass.booleanValue
		Assert.assertEquals(1, fieldNum);
		
		int sizeOf = in.readInt32();
		//System.out.println("serialized size of booleanValue = " + sizeOf);

		fieldNum = in.readFieldNumber(null);
		Assert.assertEquals(15, fieldNum); // ArrayObject.ID_ARRAY
		String className = in.readString();
		Assert.assertEquals("boolean", className);
	
		fieldNum = in.readFieldNumber(null);
		Assert.assertEquals(3, fieldNum); // ArrayObject.ID_ARRAY_LEN
		int len = in.readUInt32();
		Assert.assertEquals(1, len);
		
		fieldNum = in.readFieldNumber(null);
		Assert.assertEquals(2, fieldNum); // ArrayObject.ID_ARRAY_DIMENSION
		int dimension = in.readUInt32();
		Assert.assertEquals(1, dimension);
		
		fieldNum = in.readFieldNumber(null);
		Assert.assertEquals(1, fieldNum); // ArrayObject.ID_OBJECT
		sizeOf = in.readUInt32();
		//System.out.println("serialized size of boolean = " + sizeOf);
		
		fieldNum = in.readFieldNumber(null);
		Assert.assertEquals(1, fieldNum); // ArrayObject.ID_BOOL
		boolean value = in.readBool();
		//System.out.println("value=" + value);
		
		fieldNum = in.readFieldNumber(null);
		Assert.assertEquals(0, fieldNum); // ArrayObject.END
		
		fieldNum = in.readFieldNumber(null);
		Assert.assertEquals(0, fieldNum); // PrimitiveArraysClass.END
		
		ProtobufIOUtil.mergeFrom(blob, instance, schema);
		
	}
}
