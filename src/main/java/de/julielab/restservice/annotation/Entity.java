package de.julielab.restservice.annotation;

public class Entity {
	public String type;
	public int start;
	public int end;
	public String text;

	public Entity(String type, int start, int end, String text) {
		this.type = type;
		this.start = start;
		this.end = end;
		this.text = text;
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
		result = prime * result + this.end;
		result = prime * result + this.start;
		result = prime * result
				+ ((this.text == null) ? 0 : this.text.hashCode());
		result = prime * result
				+ ((this.type == null) ? 0 : this.type.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Entity))
			return false;
		Entity other = (Entity) obj;
		if (this.end != other.end)
			return false;
		if (this.start != other.start)
			return false;
		if (this.text == null) {
			if (other.text != null)
				return false;
		} else if (!this.text.equals(other.text))
			return false;
		if (this.type == null) {
			if (other.type != null)
				return false;
		} else if (!this.type.equals(other.type))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Entity [type=" + this.type + ", start=" + this.start + ", end="
				+ this.end + ", text=" + this.text + "]";
	}
}