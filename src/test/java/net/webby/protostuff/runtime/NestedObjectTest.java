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
import com.dyuproject.protostuff.parser.Field.Modifier;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * 
 * @author Alex Shvid
 *
 */

public class NestedObjectTest {

	@Test
	public void test() throws Exception {
		
		Schema<NestedObjectClass> schema = RuntimeSchema.getSchema(NestedObjectClass.class);
		
		String content = Generators.newProtoGenerator(schema).generate();
		
		//System.out.println(content);
		
		Proto proto = new Proto(new File("test.proto"));
		ProtoUtil.loadFrom(new ByteArrayInputStream(content.getBytes()), proto);
		 
		String packageName = this.getClass().getPackage().getName();
		
		Assert.assertEquals(proto.getPackageName(), packageName.replace('.', '_'));
		Assert.assertEquals(proto.getJavaPackageName(), packageName);
		Assert.assertEquals(0, proto.getEnumGroups().size());
		Assert.assertEquals(2, proto.getMessages().size());
		
		Message msg = proto.getMessage("NestedObjectClass");
		Assert.assertNotNull(msg);
		
		for (Field<?> field : msg.getFieldMap().values()) {
			Assert.assertEquals(Modifier.OPTIONAL, field.getModifier());
		}
		
		Assert.assertEquals(1,  msg.getFieldCount());
		Assert.assertEquals("NestedObject", msg.getField("nestedObj").getJavaType());
		
		
		msg = proto.getMessage("NestedObject");
		Assert.assertNotNull(msg);
		
		for (Field<?> field : msg.getFieldMap().values()) {
			Assert.assertEquals(Modifier.OPTIONAL, field.getModifier());
		}
		
		Assert.assertEquals(1,  msg.getFieldCount());
		Assert.assertTrue(msg.getField("value") instanceof Field.String);
	}
	
}
