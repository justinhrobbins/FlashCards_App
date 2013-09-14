package org.robbins.flashcards.dto;

import java.io.Serializable;

public class Tag implements Serializable {

	private static final long serialVersionUID = 3642775570292807703L;

	private Long id;
	private String name;

	public Tag() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Tag(Long id) {
		setId(id);
	}

	public Tag(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * toString
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(getClass().getName()).append("@")
				.append(Integer.toHexString(hashCode())).append(" [");
		buffer.append("id").append("='").append(getId()).append("' ");
		buffer.append("name").append("='").append(getName()).append("' ");
		buffer.append("]");

		return buffer.toString();
	}
}