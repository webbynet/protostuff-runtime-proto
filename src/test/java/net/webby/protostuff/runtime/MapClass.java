package net.webby.protostuff.runtime;

import java.util.Map;

import com.dyuproject.protostuff.Tag;

public class MapClass {

	@Tag(1)
	protected Map<String, String> stringToString;

	@Tag(2)
	protected Map<String, Object> stringToObject;

	@Tag(3)
	protected Map<Object, Object> objectToObject;

}
