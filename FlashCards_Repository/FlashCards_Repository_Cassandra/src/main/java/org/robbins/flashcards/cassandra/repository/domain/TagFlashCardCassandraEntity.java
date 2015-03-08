package org.robbins.flashcards.cassandra.repository.domain;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.io.Serializable;
import java.util.UUID;

@Table(value = "tag_flashcard")
public class TagFlashCardCassandraEntity implements Serializable {

    @PrimaryKey
    private TagFlashCardKey id;

    @Column
    private String question;

    @Column
    private String answer;

    public TagFlashCardKey getId() {
        return id;
    }

    public void setId(TagFlashCardKey id) {
        this.id = id;
    }

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
}
