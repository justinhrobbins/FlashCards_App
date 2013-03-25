package org.robbins.flashcards.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.robbins.flashcards.model.common.AbstractAuditable;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "tag")
@AttributeOverride(name="id", column=@Column(name="TagId"))
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
@JsonFilter("apiFilter")
public class Tag extends AbstractAuditable<User, Long> implements Serializable {

	private static final long serialVersionUID = 3642775570292807703L;

	@Column(name = "TagName", unique = true, nullable = false)
	private String name;

	@ManyToMany(targetEntity = org.robbins.flashcards.model.FlashCard.class, cascade = {
			CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "tags", fetch = FetchType.LAZY)
	@OrderBy("question")
	private Set<FlashCard> flashcards = new HashSet<FlashCard>(0);

	public Tag() {
	}

	public Tag(Long id) {
		setId(id);
	}

	public Tag(String name) {
		this.name = name;
	}

	public Tag(String name, Set<FlashCard> flashcards) {
		this.name = name;
		this.flashcards = flashcards;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<FlashCard> getFlashcards() {
		return this.flashcards;
	}

	public void setFlashcards(Set<FlashCard> flashcards) {
		this.flashcards = flashcards;
	}

	@JsonIgnore
	@Transient
	public List<FlashCard> getFlashcardsAsList() {
		if (getFlashcards() == null) {
			return null;
		}
		List<FlashCard> flashCards = new ArrayList<FlashCard>();
		for (FlashCard flashCard : getFlashcards()) {
			flashCards.add(flashCard);
		}
		return flashCards;
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