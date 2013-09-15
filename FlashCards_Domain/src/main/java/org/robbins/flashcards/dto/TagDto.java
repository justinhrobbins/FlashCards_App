package org.robbins.flashcards.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TagDto implements Serializable {

	private static final long serialVersionUID = 3642775570292807703L;

	private Long id;
	private String name;

	public TagDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public TagDto(Long id) {
		setId(id);
	}

	public TagDto(String name) {
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