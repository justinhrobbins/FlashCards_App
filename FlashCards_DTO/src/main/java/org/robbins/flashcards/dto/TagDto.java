
package org.robbins.flashcards.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
@XmlRootElement(name = "tag")
@XmlAccessorType(XmlAccessType.FIELD)
public class TagDto extends AbstractAuditableDto implements Serializable {

    private static final long serialVersionUID = 3642775570292807703L;

    private String name;

    private Set<FlashCardDto> flashcards = new HashSet<FlashCardDto>(0);

    public TagDto() {
    }

    public TagDto(final Long id) {
        setId(id);
    }

    public TagDto(final Long id, final String name) {
        setId(id);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Set<FlashCardDto> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(final Set<FlashCardDto> flashcards) {
        this.flashcards = flashcards;
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
