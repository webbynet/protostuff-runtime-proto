package net.webby.protostuff.runtime;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.WireFormat.FieldType;
import com.dyuproject.protostuff.runtime.HasSchema;
import com.dyuproject.protostuff.runtime.MappedSchema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * Proto Generator from Protostuff Runtime Schema
 * 
 * 
 * @author Alex Shvid
 *
 */

public class RuntimeProtoGenerator implements ProtoGenerator {

	private final Schema<?> schema;
	private String packageName;
	private String javaPackageName;
	private String outerClassName = null;
	private Set<String> generatedMessages = new HashSet<String>();
	private StringBuilder output = new StringBuilder();
	
	public RuntimeProtoGenerator(Schema<?> schema) {
		this.schema = schema;
		if (!(schema instanceof RuntimeSchema)) {
			throw new IllegalArgumentException("schema instance must be a RuntimeSchema");
		}
		
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
		
		output.append("option java_package = \"").append(javaPackageName).append("\";\n");
		
		if (outerClassName != null) {
			output.append("option java_outer_classname=\"").append(outerClassName).append("\";\n");
		}
		
		output.append("\n");

		doGenerateProto(schema);

	}

	protected void doGenerateProto(Schema<?> schema) {

		Map<String, Message> generateAdditionalMessages = null;

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
				if (field.type == FieldType.MESSAGE) {

					Class<?> messageFieldClass = findMessageFieldClass(field);
					if (messageFieldClass == null) {
						throw new IllegalStateException(
								"expected RuntimeMessageField for message field, probably invalid protostuff version");
					}

					Field typeClassField = messageFieldClass.getDeclaredField("typeClass");
					typeClassField.setAccessible(true);
					Class<?> typeClass = (Class<?>) typeClassField.get(field);

					Field hasSchemaField = messageFieldClass.getDeclaredField("hasSchema");
					hasSchemaField.setAccessible(true);

					HasSchema<?> hasSchema = (HasSchema<?>) hasSchemaField.get(field);
					Schema<?> fieldSchema = hasSchema.getSchema();
					fieldType = fieldSchema.messageName();

					if (!generatedMessages.contains(fieldType)) {
						if (generateAdditionalMessages == null) {
							generateAdditionalMessages = new HashMap<String, Message>();
						}
						generateAdditionalMessages.put(fieldType, new Message(typeClass, fieldSchema));
					}

				} else {
					fieldType = field.type.toString().toLowerCase();
				}

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

		if (generateAdditionalMessages != null) {
			for (Map.Entry<String, Message> entry : generateAdditionalMessages.entrySet()) {
				generatedMessages.add(entry.getKey());
				doGenerateProto(entry.getValue().schema);
			}
		}

	}

	private Class<?> findMessageFieldClass(com.dyuproject.protostuff.runtime.MappedSchema.Field<?> field) {
		Class<?> fieldClass = field.getClass();
		while (fieldClass != Object.class) {
			if (fieldClass.getSimpleName().equals("RuntimeMessageField")) {
				return fieldClass;
			}
			fieldClass = fieldClass.getSuperclass();
		}
		return null;
	}

	private static final class Message {

		final Class<?> typeClass;
		final Schema<?> schema;

		Message(Class<?> typeClass, Schema<?> schema) {
			this.typeClass = typeClass;
			this.schema = schema;
		}
	}
	
}
