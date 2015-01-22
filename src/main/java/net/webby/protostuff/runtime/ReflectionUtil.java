package net.webby.protostuff.runtime;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 
 * @author Alex Shvid
 *
 */


public final class ReflectionUtil {

	private ReflectionUtil() {
	}
	
	public static Pair<Type, Type> getMapGenericTypes(Type type) {
		if (!(type instanceof ParameterizedType)) {
			return new Pair<Type, Type>(Object.class, Object.class);
		}
		
    ParameterizedType parameterized = (ParameterizedType) type;

    Type keyType = parameterized.getActualTypeArguments()[0];
    Type valueType = parameterized.getActualTypeArguments()[1];
    
    return Pair.newPair(keyType, valueType);
	}
	
  public static String getClassHierarchy(Class<?> cls) {
  	StringBuilder str = new StringBuilder();
		while (cls != Object.class) {
			if (str.length() > 0) {
				str.append(" < ");
			}
			str.append(cls.getName());
			cls = cls.getSuperclass();
		}
		return str.toString();
  }
  
	public static Pair<RuntimeSchemaType, Class<?>> normalizeSchemaClass(Class<?> schemaClass) {
		while (schemaClass != Object.class) {
			RuntimeSchemaType type = RuntimeSchemaType.findByName(schemaClass.getSimpleName());
			if (type != null) {
				return new Pair<RuntimeSchemaType, Class<?>>(type, schemaClass);
			}
			schemaClass = schemaClass.getSuperclass();
		}
		return null;
	}
	
	public static Pair<RuntimeFieldType, Class<?>> normalizeFieldClass(com.dyuproject.protostuff.runtime.MappedSchema.Field<?> field) {
		Class<?> fieldClass = field.getClass();
		while (fieldClass != Object.class) {
			RuntimeFieldType type = RuntimeFieldType.findByName(fieldClass.getSimpleName());
			if (type != null) {
				return new Pair<RuntimeFieldType, Class<?>>(type, fieldClass);
			}
			fieldClass = fieldClass.getSuperclass();
		}
		return null;
	}
}
