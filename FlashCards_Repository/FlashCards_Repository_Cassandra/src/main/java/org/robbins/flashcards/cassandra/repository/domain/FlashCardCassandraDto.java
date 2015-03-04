package org.robbins.flashcards.cassandra.repository.domain;

import org.springframework.data.cassandra.mapping.Table;

import java.io.Serializable;

@Table(value = "flashcard")
public class FlashCardCassandraDto extends AbstractPersistable implements Serializable {

    private String question;
    private String answer;

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
