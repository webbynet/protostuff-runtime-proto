package net.webby.protostuff.runtime;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.dyuproject.protostuff.ByteArrayInput;
import com.dyuproject.protostuff.Input;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.parser.Field;
import com.dyuproject.protostuff.parser.Field.Modifier;
import com.dyuproject.protostuff.parser.Message;
import com.dyuproject.protostuff.parser.MessageField;
import com.dyuproject.protostuff.parser.Proto;
import com.dyuproject.protostuff.parser.ProtoUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * 
 * @author Alex Shvid
 *
 */


public class ListTest {

	private Schema<ListClass> collectionSchema = RuntimeSchema.getSchema(ListClass.class);
	private LinkedBuffer buffer = LinkedBuffer.allocate(4096);
	
	@Test
	public void testGenerator() throws Exception {
		
		String content = Generators.newProtoGenerator(collectionSchema).generate();
		
		//System.out.println(content);

		Proto proto = new Proto(new File("test.proto"));
		ProtoUtil.loadFrom(new ByteArrayInputStream(content.getBytes()), proto);
		 
		String packageName = this.getClass().getPackage().getName();
		
		Assert.assertEquals(proto.getPackageName(), packageName.replace('.', '_'));
		Assert.assertEquals(proto.getJavaPackageName(), packageName);
		Assert.assertEquals(0, proto.getEnumGroups().size());
		Assert.assertEquals(1, proto.getMessages().size());
		
		Message msg = proto.getMessage("ListClass");
		Assert.assertNotNull(msg);
		
		for (Field<?> field : msg.getFieldMap().values()) {
			Assert.assertEquals(Modifier.REPEATED, field.getModifier());
		}
		
		Assert.assertEquals(14,  msg.getFieldCount());
		Assert.assertTrue(msg.getField("booleanValue") instanceof Field.Bool);
		Assert.assertTrue(msg.getField("byteValue") instanceof Field.UInt32);
		Assert.assertTrue(msg.getField("charValue") instanceof Field.UInt32);
		Assert.assertTrue(msg.getField("shortValue") instanceof Field.UInt32);
		Assert.assertTrue(msg.getField("intValue") instanceof Field.Int32);
		Assert.assertTrue(msg.getField("longValue") instanceof Field.Int64);
		Assert.assertTrue(msg.getField("floatValue") instanceof Field.Float);
		Assert.assertTrue(msg.getField("doubleValue") instanceof Field.Double);
		Assert.assertEquals("DynamicObject", msg.getField("objValue").getJavaType());
		Assert.assertTrue(msg.getField("stringValue") instanceof Field.String);
		Assert.assertTrue(msg.getField("dateValue") instanceof Field.Fixed64);
		Assert.assertTrue(msg.getField("biValue") instanceof Field.Bytes);
		Assert.assertTrue(msg.getField("bdValue") instanceof Field.String);
		Assert.assertTrue(msg.getField("uuidValue") instanceof MessageField);
	}
	
	@Test
	public void testIO() throws Exception {
		
		ListClass ins = new ListClass();
		ins.stringValue = new ArrayList<String>();
		ins.stringValue.add("test1");
		ins.stringValue.add("test2");
		
		byte[] blob = ProtobufIOUtil.toByteArray(ins, collectionSchema, buffer);
		
		Input in = new ByteArrayInput(blob, false);

		Assert.assertEquals(10, in.readFieldNumber(null)); // ListClass.stringValue
	  Assert.assertEquals("test1", in.readString());
	  
		Assert.assertEquals(10, in.readFieldNumber(null)); // ListClass.stringValue
	  Assert.assertEquals("test2", in.readString());
	  
	  Assert.assertEquals(0, in.readFieldNumber(null)); // ListClass.END
		
	}

	@Test
	public void testArrayList() throws Exception {
		testImpl(new ArrayList<String>());
	}
	
	@Test
	public void testLinkedList() throws Exception {
		testImpl(new LinkedList<String>());
	}
	
	private void testImpl(List<String> listImpl) throws Exception {
		
		ListClass ins = new ListClass();
		ins.stringValue = listImpl;
		ins.stringValue.add("test1");
		ins.stringValue.add("test2");
		
		byte[] blob = ProtobufIOUtil.toByteArray(ins, collectionSchema, buffer);
		
		ListClass message = collectionSchema.newMessage();
		ProtobufIOUtil.mergeFrom(blob, message, collectionSchema);
		
		Assert.assertEquals(ArrayList.class, message.stringValue.getClass());
	}
	
}
