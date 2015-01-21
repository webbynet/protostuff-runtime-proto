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
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class CommonTest {

	@Test
	public void test() throws Exception {

		Schema<CommonClass> schema = RuntimeSchema.getSchema(CommonClass.class);
		
		String content = Generators.newRuntimeProtoGenerator(schema).generate();
		
		System.out.println(content);
		
		Proto proto = new Proto(new File("test.proto"));
		ProtoUtil.loadFrom(new ByteArrayInputStream(content.getBytes()), proto);
		 
		String packageName = this.getClass().getPackage().getName();
		
		Assert.assertEquals(proto.getPackageName(), packageName.replace('.', '_'));
		Assert.assertEquals(proto.getJavaPackageName(), packageName);
		Assert.assertEquals(0, proto.getEnumGroups().size());
		Assert.assertEquals(2, proto.getMessages().size());
		
		Message commonMsg = proto.getMessage("CommonClass");
		Assert.assertNotNull(commonMsg);
		
		Assert.assertEquals(5,  commonMsg.getFieldCount());
		Assert.assertTrue(commonMsg.getField("stringValue") instanceof Field.String);
		Assert.assertTrue(commonMsg.getField("dateValue") instanceof Field.Fixed64);
		Assert.assertTrue(commonMsg.getField("biValue") instanceof Field.Bytes);
		Assert.assertTrue(commonMsg.getField("bdValue") instanceof Field.String);
		Assert.assertTrue(commonMsg.getField("uuidValue") instanceof MessageField);
		
		Message uuidMsg = proto.getMessage("UUID");
		Assert.assertEquals(2,  uuidMsg.getFieldCount());
		Assert.assertTrue(uuidMsg.getField("mostSigBits") instanceof Field.Int64);
		Assert.assertTrue(uuidMsg.getField("leastSigBits") instanceof Field.Int64);
		
	}
	
}
