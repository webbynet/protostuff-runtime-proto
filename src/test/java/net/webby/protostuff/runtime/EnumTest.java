package net.webby.protostuff.runtime;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.junit.Assert;
import org.junit.Test;

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

	@Test
	public void test() throws Exception {

		Schema<EnumClass> schema = RuntimeSchema.getSchema(EnumClass.class);
		
		String content = Generators.newProtoGenerator(schema).generate();
		
		System.out.println(content);
		
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
		
		Assert.assertEquals(2,  msg.getFieldCount());
		Assert.assertEquals("SimpleEnum", msg.getField("simpleEnum").getJavaType());
		Assert.assertEquals("StringEnum", msg.getField("stringEnum").getJavaType());
		
		EnumGroup enumGroup = proto.getEnumGroup("SimpleEnum");
		Assert.assertEquals(2, enumGroup.getValueCount());
		Assert.assertEquals("VALUE1", enumGroup.getValue(0).getName());
		Assert.assertEquals("VALUE2", enumGroup.getValue(1).getName());

		enumGroup = proto.getEnumGroup("StringEnum");
		Assert.assertEquals(2, enumGroup.getValueCount());
		Assert.assertEquals("VALUE1", enumGroup.getValue(0).getName());
		Assert.assertEquals("VALUE2", enumGroup.getValue(1).getName());

	}
	
}
