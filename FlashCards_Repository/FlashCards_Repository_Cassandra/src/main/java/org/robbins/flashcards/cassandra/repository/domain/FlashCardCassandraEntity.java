package org.robbins.flashcards.cassandra.repository.domain;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.Table;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Table(value = "flashcard")
public class FlashCardCassandraEntity extends AbstractPersistable implements Serializable {

    @Column
    private String question;

    @Column
    private String answer;

    @Column("tags")
    private Map<UUID, String> tags = new HashMap<>();

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Map<UUID, String> getTags() {
        return tags;
    }
    public void setTags(Map<UUID, String> tags) {
        this.tags = tags;
    }
}
