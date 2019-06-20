package de.julielab.restservice.annotation;

public class Entity {
	public String type;
	public int start;
	public int end;
	public String text;

	public Entity(final String type, final int start, final int end,
			final String text) {
		this.type = type;
		this.start = start;
		this.end = end;
		this.text = text;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Entity))
			return false;
		final Entity other = (Entity) obj;
		if (end != other.end)
			return false;
		if (start != other.start)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + end;
		result = (prime * result) + start;
		result = (prime * result) + ((text == null) ? 0 : text.hashCode());
		result = (prime * result) + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Entity [type=" + type + ", start=" + start + ", end=" + end
				+ ", text=" + text + "]";
	}
}