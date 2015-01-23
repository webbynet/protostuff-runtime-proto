package net.webby.protostuff.runtime;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.parser.EnumGroup;
import com.dyuproject.protostuff.parser.Field;
import com.dyuproject.protostuff.parser.Field.Modifier;
import com.dyuproject.protostuff.parser.Message;
import com.dyuproject.protostuff.parser.Proto;
import com.dyuproject.protostuff.parser.ProtoUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * 
 * @author Alex Shvid
 *
 */

public class EnumTest {

	private Schema<EnumClass> schema = RuntimeSchema.getSchema(EnumClass.class);
	private Schema<EnumObjectClass> enumObjectSchema = RuntimeSchema.getSchema(EnumObjectClass.class);
	private LinkedBuffer buffer = LinkedBuffer.allocate(4096);

	@Test
	public void test() throws Exception {
		
		String content = Generators.newProtoGenerator(schema).generate();
		//System.out.println(content);
		
		Proto proto = new Proto(new File("test.proto"));
		ProtoUtil.loadFrom(new ByteArrayInputStream(content.getBytes()), proto);
		 
		String packageName = this.getClass().getPackage().getName();
		
		Assert.assertEquals(proto.getPackageName(), packageName.replace('.', '_'));
		Assert.assertEquals(proto.getJavaPackageName(), packageName);
		Assert.assertEquals(2, proto.getEnumGroups().size());
		Assert.assertEquals(1, proto.getMessages().size());
		
		Message msg = proto.getMessage("EnumClass");
		Assert.assertNotNull(msg);
		
		for (Field<?> field : msg.getFieldMap().values()) {
			Assert.assertEquals(Modifier.OPTIONAL, field.getModifier());
		}
		
		Assert.assertEquals(3,  msg.getFieldCount());
		Assert.assertEquals("SimpleEnum", msg.getField("simpleEnum").getJavaType());
		Assert.assertEquals("StringEnum", msg.getField("stringEnum").getJavaType());
		Assert.assertEquals("EnumObject", msg.getField("enumValue").getJavaType());
		
		EnumGroup enumGroup = proto.getEnumGroup("SimpleEnum");
		Assert.assertEquals(2, enumGroup.getValueCount());
		Assert.assertEquals("VALUE1", enumGroup.getValue(0).getName());
		Assert.assertEquals("VALUE2", enumGroup.getValue(1).getName());

		enumGroup = proto.getEnumGroup("StringEnum");
		Assert.assertEquals(2, enumGroup.getValueCount());
		Assert.assertEquals("VALUE1", enumGroup.getValue(0).getName());
		Assert.assertEquals("VALUE2", enumGroup.getValue(1).getName());

	}

	@Test
	public void testSimpleEnum() throws Exception {
		
		EnumClass ins = new EnumClass();
		ins.simpleEnum = SimpleEnum.VALUE1;
		
		byte[] blob = ProtobufIOUtil.toByteArray(ins, schema, buffer);

		EnumObjectClass message = enumObjectSchema.newMessage();
		ProtobufIOUtil.mergeFrom(blob, message, enumObjectSchema);
		
		Assert.assertEquals(ins.simpleEnum, message.simpleEnum);
		
	}
	
	@Test
	public void testDynamicEnum() throws Exception {
		
		EnumClass ins = new EnumClass();
		ins.enumValue = SimpleEnum.VALUE1;
		
		byte[] blob = ProtobufIOUtil.toByteArray(ins, schema, buffer);

		EnumObjectClass message = enumObjectSchema.newMessage();
		ProtobufIOUtil.mergeFrom(blob, message, enumObjectSchema);
		
		Assert.assertEquals(0, message.enumValue.ordinal);
		Assert.assertEquals(SimpleEnum.class.getName(), message.enumValue.enumId);
		
	}
	
}
