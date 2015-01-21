package net.webby.protostuff.runtime;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.parser.Field;
import com.dyuproject.protostuff.parser.Message;
import com.dyuproject.protostuff.parser.Proto;
import com.dyuproject.protostuff.parser.ProtoUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class WrapperTest {

	@Test
	public void test() throws Exception {
		
		Schema<WrapperClass> schema = RuntimeSchema.getSchema(WrapperClass.class);
		
		String content = Generators.newProtoGenerator(schema).generate();
		
		System.out.println(content);
		
		Proto proto = new Proto(new File("test.proto"));
		ProtoUtil.loadFrom(new ByteArrayInputStream(content.getBytes()), proto);
		 
		String packageName = this.getClass().getPackage().getName();
		
		Assert.assertEquals(proto.getPackageName(), packageName.replace('.', '_'));
		Assert.assertEquals(proto.getJavaPackageName(), packageName);
		Assert.assertEquals(0, proto.getEnumGroups().size());
		Assert.assertEquals(1, proto.getMessages().size());
		
		Message primitiveMsg = proto.getMessage("WrapperClass");
		Assert.assertNotNull(primitiveMsg);
		
		Assert.assertEquals(8,  primitiveMsg.getFieldCount());
		Assert.assertTrue(primitiveMsg.getField("booleanValue") instanceof Field.Bool);
		Assert.assertTrue(primitiveMsg.getField("byteValue") instanceof Field.UInt32);
		Assert.assertTrue(primitiveMsg.getField("shortValue") instanceof Field.UInt32);
		Assert.assertTrue(primitiveMsg.getField("intValue") instanceof Field.Int32);
		Assert.assertTrue(primitiveMsg.getField("longValue") instanceof Field.Int64);
		Assert.assertTrue(primitiveMsg.getField("floatValue") instanceof Field.Float);
		Assert.assertTrue(primitiveMsg.getField("doubleValue") instanceof Field.Double);
		Assert.assertEquals("DynamicObject", primitiveMsg.getField("objValue").getJavaType());
	}
	
}
