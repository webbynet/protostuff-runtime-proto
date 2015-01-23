package net.webby.protostuff.runtime;

/**
 * 
 * @author Alex Shvid
 *
 */


import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.WireFormat.FieldType;
import com.dyuproject.protostuff.runtime.EnumIO;
import com.dyuproject.protostuff.runtime.HasSchema;
import com.dyuproject.protostuff.runtime.MappedSchema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * Proto File Generator from Protostuff Runtime Schema
 * 
 * 
 * @author Alex Shvid
 *
 */

public class RuntimeProtoGenerator implements ProtoGenerator {

	private static final Class<?>[] knownTypes = { java.util.UUID.class };
	
	static {
		Arrays.sort(knownTypes, ClassNameComparator.INSTANCE);
	}
	
	public enum ClassNameComparator implements Comparator<Class<?>> {
		
		INSTANCE;

		@Override
		public int compare(Class<?> arg0, Class<?> arg1) {
			return arg0.getName().compareTo(arg1.getName());
		}
		
	}
	
	private final Schema<?> schema;
	private String packageName;
	private String javaPackageName;
	private String outerClassName = null;
	private Set<String> generatedMessages = new HashSet<String>();
	private Map<String, Object> generateAdditionalMessages = null;
	private StringBuilder output = new StringBuilder();
	
	public RuntimeProtoGenerator(Schema<?> schema) {
		if (!(schema instanceof RuntimeSchema)) {
			throw new IllegalArgumentException("schema instance must be a RuntimeSchema");
		}
		
		this.schema = schema;
		Class<?> typeClass = schema.typeClass();
		this.javaPackageName = typeClass.getPackage().getName();
		this.packageName = this.javaPackageName.replace('.', '_');

	}
	
	@Override
	public ProtoGenerator setJavaOuterClassName(String outerClassName) {
		this.outerClassName = outerClassName;
		return this;
	}

	@Override
	public ProtoGenerator setPackageName(String packageName) {
		this.packageName = packageName;
		return this;
	}
	
	@Override
	public ProtoGenerator setJavaPackageName(String packageName) {
		this.javaPackageName = packageName;
		return this;
	}
	
	@Override
	public String generate() {
		if (output.length() == 0) {
			generateInternal();
		}
		return output.toString();
	}

	public void generateInternal() {

		output.append("package ").append(packageName).append(";\n\n");

		output.append("import \"protostuff-default.proto\";\n\n");

		output.append("option java_package = \"").append(javaPackageName).append("\";\n");
		
		if (outerClassName != null) {
			output.append("option java_outer_classname=\"").append(outerClassName).append("\";\n");
		}
		
		output.append("\n");

		doGenerateMessage(schema);
		
		if (generateAdditionalMessages != null) {
			while(!generateAdditionalMessages.isEmpty()) {
				String key = generateAdditionalMessages.keySet().iterator().next();
				generatedMessages.add(key);
				Object entry = generateAdditionalMessages.remove(key);
				if (entry != null) {
					if (entry instanceof Message) {
						doGenerateMessage(((Message)entry).schema);
					}
					else if (entry instanceof EnumObj) {
						doGenerateEnum(((EnumObj)entry).enumClass);
					}
				}
			}
			
		}

	}

	protected void doGenerateEnum(Class<?> enumClass) {
		
		output.append("enum ").append(enumClass.getSimpleName()).append(" {").append("\n");

		for (Object val : enumClass.getEnumConstants()) {
			Enum<?> v = (Enum<?>) val;
			output.append("  ").append(val).append(" = ").append(v.ordinal()).append(";\n");
		}
		
		output.append("}").append("\n\n");
		
	}
	
	protected void doGenerateMessage(Schema<?> schema) {

		if (!(schema instanceof RuntimeSchema)) {
			throw new IllegalStateException("invalid schema type " + schema.getClass());
		}

		RuntimeSchema<?> runtimeSchema = (RuntimeSchema<?>) schema;

		output.append("message ").append(runtimeSchema.messageName()).append(" {").append("\n");

		try {
			Field fieldsField = MappedSchema.class.getDeclaredField("fields");
			fieldsField.setAccessible(true);
			com.dyuproject.protostuff.runtime.MappedSchema.Field<?>[] fields = (com.dyuproject.protostuff.runtime.MappedSchema.Field<?>[]) fieldsField
					.get(runtimeSchema);

			for (int i = 0; i != fields.length; ++i) {

				com.dyuproject.protostuff.runtime.MappedSchema.Field<?> field = fields[i];

				String fieldType = null;
				if (field.type == FieldType.ENUM) {
					
					Field reflectionField = field.getClass().getDeclaredField("val$eio");
					reflectionField.setAccessible(true);
					EnumIO enumIO = (EnumIO) reflectionField.get(field);	

					//System.out.println("enumIO = " + enumIO.enumClass);
					
					fieldType = enumIO.enumClass.getSimpleName();
					
					if (!generatedMessages.contains(fieldType)) {
						if (generateAdditionalMessages == null) {
							generateAdditionalMessages = new HashMap<String, Object>();
						}
						generateAdditionalMessages.put(fieldType, new EnumObj(enumIO.enumClass));
					}
				}
				else if (field.type == FieldType.MESSAGE) {

					Pair<RuntimeFieldType, Class<?>> normField = ReflectionUtil.normalizeFieldClass(field);
					if (normField == null) {
						throw new IllegalStateException(
								"unknown fieldClass " + field.getClass());
					}

					Class<?> fieldClass = normField.getSecond();
					if (normField.getFirst() == RuntimeFieldType.RuntimeMessageField) {
					
						Field typeClassField = fieldClass.getDeclaredField("typeClass");
						typeClassField.setAccessible(true);
						Class<?> typeClass = (Class<?>) typeClassField.get(field);
	
						Field hasSchemaField = fieldClass.getDeclaredField("hasSchema");
						hasSchemaField.setAccessible(true);
	
						HasSchema<?> hasSchema = (HasSchema<?>) hasSchemaField.get(field);
						Schema<?> fieldSchema = hasSchema.getSchema();
						fieldType = fieldSchema.messageName();
	
						if (!generatedMessages.contains(fieldType) && Arrays.binarySearch(knownTypes, typeClass, ClassNameComparator.INSTANCE) < 0) {
							if (generateAdditionalMessages == null) {
								generateAdditionalMessages = new HashMap<String, Object>();
							}
							generateAdditionalMessages.put(fieldType, new Message(typeClass, fieldSchema));
						}
					
					}
					else if (normField.getFirst() == RuntimeFieldType.RuntimeMapField || 
							normField.getFirst() == RuntimeFieldType.RuntimeObjectField) {
						
						Field schemaField = fieldClass.getDeclaredField("schema");
						schemaField.setAccessible(true);
						
						Schema<?> fieldSchema = (Schema<?>) schemaField.get(field);
						Pair<RuntimeSchemaType, Class<?>> normSchema = ReflectionUtil.normalizeSchemaClass(fieldSchema.getClass());
						if (normSchema == null) {
							throw new IllegalStateException("unknown schema type " + fieldSchema.getClass());
						}
						
						switch(normSchema.getFirst()) {
						case ArraySchema:
							fieldType = "ArrayObject";
							break;
						case ObjectSchema:
							fieldType = "DynamicObject";
							break;
						case MapSchema:
							
							Field reflectionField = field.getClass().getDeclaredField("val$f");
							reflectionField.setAccessible(true);
							Field pojoField = (Field) reflectionField.get(field);
				
							Pair<Type, Type> keyValue = ReflectionUtil.getMapGenericTypes(pojoField.getGenericType());
							
							fieldType = getMapFieldType(keyValue);
							break;							
						}
						
						//System.out.println(getClassHierarchy(normSchema.getSecond()));
						
					}
					else {
						throw new IllegalStateException("unknown fieldClass " + field.getClass());
					}

				} else {
					fieldType = field.type.toString().toLowerCase();
				}

				output.append("  ");
				
				if (field.repeated) {
					output.append("repeated ");
				} else {
					output.append("optional ");
				}

				output.append(fieldType).append(" ").append(field.name).append(" = ").append(field.number).append(";\n");

			}

		} catch (Exception e) {
			throw new RuntimeException("generate proto fail", e);
		}

		output.append("}").append("\n\n");

	}
	
	private static final class EnumObj {
		
		final Class<?> enumClass;
		
		EnumObj(Class<?> enumClass) {
			this.enumClass = enumClass;
		}
		
	}

	private static final class Message {

		final Class<?> typeClass;
		final Schema<?> schema;

		Message(Class<?> typeClass, Schema<?> schema) {
			this.typeClass = typeClass;
			this.schema = schema;
		}
	}
	
	private static String getMapFieldType(Pair<Type, Type> keyValue) {
		if (keyValue.getFirst() == String.class) {
			if (keyValue.getSecond() == String.class) {
				return "MapStringString";
			}
			else {
				return "MapStringObject";
			}
		}
		return "MapObjectObject";
	}
	
}
