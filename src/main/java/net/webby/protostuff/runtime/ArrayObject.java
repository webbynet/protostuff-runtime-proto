package net.webby.protostuff.runtime;

import java.util.List;

import com.dyuproject.protostuff.Tag;

public final class ArrayObject {

	@Tag(Constants.ID_ARRAY)
	public int id;
	
	@Tag(Constants.ID_ARRAY_MAPPED)
	public int mapped;
	
	@Tag(Constants.ID_ARRAY_LEN)
	public int len;
	
	@Tag(Constants.ID_ARRAY_DIMENSION)
	public int dimension;
	
	@Tag(Constants.ID_ARRAY_VALUE)
	public List<DynamicObject> value;
	
}
