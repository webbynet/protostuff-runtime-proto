package net.webby.protostuff.runtime;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * Util class that utilizes ThreadLocal to store LinkedBuffer.
 * 
 * @author Alex Shvid
 *
 */

public final class ProtostuffThreadLocal {

	private static final int linkedBufferSize = Integer.getInteger("protostuff.threadbuffer.size", 4096);

	private static final ThreadLocal<LinkedBuffer> localLinkedBuffer = new ThreadLocal<LinkedBuffer>() {

		@Override
		protected LinkedBuffer initialValue() {
			return LinkedBuffer.allocate(linkedBufferSize);
		}

	};

	public interface Instantiator<T> {
		
		T newInstance();
		
	}
	
	private ProtostuffThreadLocal() {
	}

	public static <T> T fromBytes(byte[] protobuf, Class<T> schemaCls) {
		Schema<T> schema = RuntimeSchema.getSchema(schemaCls);
		return fromBytes(protobuf, schema, null);
	}
	
	public static <T> T fromBytes(byte[] protobuf, Class<T> schemaCls, Instantiator<T> instantiatorOptional) {
		Schema<T> schema = RuntimeSchema.getSchema(schemaCls);
		return fromBytes(protobuf, schema, instantiatorOptional);
	}

	public static <T> T fromBytes(byte[] protobuf, Schema<T> schema, Instantiator<T> instantiatorOptional) {
		T newInstance = null;
		if (instantiatorOptional != null) {
			newInstance = instantiatorOptional.newInstance();
		}
		else {
		  newInstance = schema.newMessage();
		}
		ProtobufIOUtil.mergeFrom(protobuf, newInstance, schema);
		return newInstance;
	}

	public static <T> byte[] toBytes(T obj) {
		@SuppressWarnings("unchecked")
		Class<T> schemaCls = (Class<T>) obj.getClass();
		return toBytes(schemaCls, obj);
	}

	public static <T> byte[] toBytes(Class<T> schemaCls, T obj) {
		Schema<T> schema = RuntimeSchema.getSchema(schemaCls);
		return toBytes(schema, obj);
	}
	
	public static <T> byte[] toBytes(Schema<T> schema, T obj) {
		return ProtobufIOUtil.toByteArray(obj, schema, localLinkedBuffer.get().clear());
	}

}
