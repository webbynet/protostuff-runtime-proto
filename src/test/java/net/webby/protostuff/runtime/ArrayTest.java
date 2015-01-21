package net.webby.protostuff.runtime;

import org.junit.Test;

import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class ArrayTest {

	@Test
	public void test() throws Exception {
		
		Schema<ArrayObject> schema = RuntimeSchema.getSchema(ArrayObject.class);
		
		String content = Generators.newProtoGenerator(schema).generate();
		
		System.out.println(content);
		
	}
	
}
