
package org.robbins.flashcards.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TagDto extends AbstractAuditable<String, String> implements Serializable {

    private static final long serialVersionUID = 3642775570292807703L;

    private String name;

    private Set<FlashCardDto> flashCards = new HashSet<FlashCardDto>(0);

    public TagDto() {
    }

    public TagDto(final String name) {
        this.name = name;
    }

    public TagDto(final String name, final Set<FlashCardDto> flashCards) {
        this.name = name;
        this.flashCards = flashCards;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Set<FlashCardDto> getFlashCards() {
        return this.flashCards;
    }

    public void setFlashCards(final Set<FlashCardDto> flashCards) {
        this.flashCards = flashCards;
    }

    public List<FlashCardDto> getFlashCardsAsList() {
        if (getFlashCards() == null) {
            return null;
        }
        List<FlashCardDto> flashCards = new ArrayList<FlashCardDto>();
        for (FlashCardDto flashCard : getFlashCards()) {
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
