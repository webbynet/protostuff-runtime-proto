package net.webby.protostuff.runtime;

import com.dyuproject.protostuff.Tag;

/**
 * 
 * @author Alex Shvid
 *
 */

public class JavaObject {

	@Tag(1)
	public boolean unknown = false;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (unknown ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JavaObject other = (JavaObject) obj;
		if (unknown != other.unknown)
			return false;
		return true;
	}
	
	
	
}
