
package org.robbins.flashcards.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;

public class FlashCardDto extends AbstractAuditable<UserDto, String> implements
        Serializable {

    private static final long serialVersionUID = -3461056579037652853L;

    private String question;

    private String answer;

    private Set<TagDto> tags = new HashSet<TagDto>(0);

    private List<String> links = new ArrayList<String>(0);

    public FlashCardDto() {
    }

    public FlashCardDto(final String flashCardId) {
        setId(flashCardId);
    }

    public FlashCardDto(final String question, final String answer) {
        this.question = question;
        this.answer = answer;
    }

    public FlashCardDto(final String question, final String answer,
            final Set<TagDto> tags, final List<String> links) {
        this.question = question;
        this.answer = answer;
        this.tags = tags;
        this.links = links;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(final String question) {
        this.question = question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(final String answer) {
        this.answer = answer;
    }

    public Set<TagDto> getTags() {
        return this.tags;
    }

    public void setTags(final Set<TagDto> tags) {
        this.tags = tags;
    }

    @JsonIgnore
    // @Transient
    public List<TagDto> getTagsAsList() {
        if (getTags() == null) {
            return null;
        }
        List<TagDto> tagsList = new ArrayList<TagDto>();
        for (TagDto tag : getTags()) {
            tagsList.add(tag);
        }
        return tagsList;
    }

    public List<String> getLinks() {
        return this.links;
    }

    public void setLinks(final List<String> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getClass().getName()).append("@").append(
                Integer.toHexString(hashCode())).append(" [");
        buffer.append("id").append("='").append(getId()).append("' ");
        buffer.append("question").append("='").append(getQuestion()).append("' ");
        buffer.append("answer").append("='").append(getAnswer()).append("' ");
        buffer.append("]");

        return buffer.toString();
    }
}
