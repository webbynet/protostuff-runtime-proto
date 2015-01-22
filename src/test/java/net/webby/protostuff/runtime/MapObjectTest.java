package net.webby.protostuff.runtime;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * 
 * @author Alex Shvid
 *
 */


public class MapObjectTest {

	private Schema<MapClass> mapSchema = RuntimeSchema.getSchema(MapClass.class);
	private Schema<MapObjectClass> mapObjectSchema = RuntimeSchema.getSchema(MapObjectClass.class);
	private LinkedBuffer buffer = LinkedBuffer.allocate(4096);
	
	@Test
	public void testPrint() throws Exception {
		
		Schema<MapClass> schema = RuntimeSchema.getSchema(MapClass.class);
		
		String content = Generators.newProtoGenerator(schema).generate();
		
		System.out.println(content);

	}
	
	@Test
	public void test() throws Exception {
		
		MapClass mapClass = new MapClass();
		mapClass.objectToObject = new HashMap<Object, Object>();
		mapClass.objectToObject.put("objectKey", "objectValue");

		mapClass.stringToObject = new HashMap<String, Object>();
		mapClass.stringToObject.put("stringKey", "objectValue");

		mapClass.stringToString = new HashMap<String, String>();
		mapClass.stringToString.put("stringKey", "stringValue");

		byte[] blob = ProtobufIOUtil.toByteArray(mapClass, mapSchema, buffer);
		
		MapObjectClass message = mapObjectSchema.newMessage();
		ProtobufIOUtil.mergeFrom(blob, message, mapObjectSchema);

		for (MapEntryStringString entry : message.stringToString.value) {
			Assert.assertEquals(mapClass.stringToString.get(entry.key), entry.value);
		}

		for (MapEntryStringObject entry : message.stringToObject.value) {
			Assert.assertEquals(mapClass.stringToObject.get(entry.key), entry.value.stringValue);
		}
		
		for (MapEntryObjectObject entry : message.objectToObject.value) {
			Assert.assertEquals(mapClass.objectToObject.get(entry.key.stringValue), entry.value.stringValue);
		}
	}
	
}
