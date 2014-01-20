
package org.robbins.flashcards.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TagDto extends AbstractAuditable<UserDto, Long> implements Serializable {

    private static final long serialVersionUID = 3642775570292807703L;

    private String name;

    private Set<FlashCardDto> flashcards = new HashSet<FlashCardDto>(0);

    public TagDto() {
    }

    public TagDto(Long id) {
        setId(id);
    }

    public TagDto(String name) {
        this.name = name;
    }

    public TagDto(String name, Set<FlashCardDto> flashcards) {
        this.name = name;
        this.flashcards = flashcards;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<FlashCardDto> getFlashcards() {
        return this.flashcards;
    }

    public void setFlashcards(Set<FlashCardDto> flashcards) {
        this.flashcards = flashcards;
    }

    public List<FlashCardDto> getFlashcardsAsList() {
        if (getFlashcards() == null) {
            return null;
        }
        List<FlashCardDto> flashCards = new ArrayList<FlashCardDto>();
        for (FlashCardDto flashCard : getFlashcards()) {
            flashCards.add(flashCard);
        }
        return flashCards;
    }

    /**
     * toString
     *
     * @return String
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getClass().getName()).append("@").append(
                Integer.toHexString(hashCode())).append(" [");
        buffer.append("id").append("='").append(getId()).append("' ");
        buffer.append("name").append("='").append(getName()).append("' ");
        buffer.append("]");

        return buffer.toString();
    }
}