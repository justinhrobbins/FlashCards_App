package org.robbins.flashcards.cassandra.repository.domain;

import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.*;

import java.io.Serializable;
import java.util.UUID;

@PrimaryKeyClass
public class TagFlashCardKey implements Serializable {

    @PrimaryKeyColumn(name = "tag_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private Long tagId;

    @PrimaryKeyColumn(name = "flashcard_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Long flashCardId;

    public TagFlashCardKey(Long tagId, Long flashCardId) {
        this.tagId = tagId;
        this.flashCardId = flashCardId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getFlashCardId() {
        return flashCardId;
    }

    public void setFlashCardId(Long flashCardId) {
        this.flashCardId = flashCardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagFlashCardKey that = (TagFlashCardKey) o;

        if (flashCardId != null ? !flashCardId.equals(that.flashCardId) : that.flashCardId != null) return false;
        if (tagId != null ? !tagId.equals(that.tagId) : that.tagId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tagId != null ? tagId.hashCode() : 0;
        result = 31 * result + (flashCardId != null ? flashCardId.hashCode() : 0);
        return result;
    }
}
