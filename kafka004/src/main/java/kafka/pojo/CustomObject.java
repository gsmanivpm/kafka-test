package kafka.pojo;

import java.io.Serializable;

public class CustomObject implements Serializable{

	@Override
	public String toString() {
		return "CustomObject [id=" + id + ", name=" + name + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
