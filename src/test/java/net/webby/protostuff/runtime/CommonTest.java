package net.webby.protostuff.runtime;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.parser.Field;
import com.dyuproject.protostuff.parser.Message;
import com.dyuproject.protostuff.parser.MessageField;
import com.dyuproject.protostuff.parser.Proto;
import com.dyuproject.protostuff.parser.ProtoUtil;
import com.dyuproject.protostuff.parser.Field.Modifier;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class CommonTest {

	@Test
	public void test() throws Exception {

		Schema<CommonClass> schema = RuntimeSchema.getSchema(CommonClass.class);
		
		String content = Generators.newProtoGenerator(schema).generate();
		
		System.out.println(content);
		
		Proto proto = new Proto(new File("test.proto"));
		ProtoUtil.loadFrom(new ByteArrayInputStream(content.getBytes()), proto);
		 
		String packageName = this.getClass().getPackage().getName();
		
		Assert.assertEquals(proto.getPackageName(), packageName.replace('.', '_'));
		Assert.assertEquals(proto.getJavaPackageName(), packageName);
		Assert.assertEquals(0, proto.getEnumGroups().size());
		Assert.assertEquals(1, proto.getMessages().size());
		
		Message msg = proto.getMessage("CommonClass");
		Assert.assertNotNull(msg);
		
		for (Field<?> field : msg.getFieldMap().values()) {
			Assert.assertEquals(Modifier.OPTIONAL, field.getModifier());
		}
		
		Assert.assertEquals(5,  msg.getFieldCount());
		Assert.assertTrue(msg.getField("stringValue") instanceof Field.String);
		Assert.assertTrue(msg.getField("dateValue") instanceof Field.Fixed64);
		Assert.assertTrue(msg.getField("biValue") instanceof Field.Bytes);
		Assert.assertTrue(msg.getField("bdValue") instanceof Field.String);
		Assert.assertTrue(msg.getField("uuidValue") instanceof MessageField);
		
	}
	
}
