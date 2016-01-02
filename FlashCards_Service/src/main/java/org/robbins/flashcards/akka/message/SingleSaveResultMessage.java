package org.robbins.flashcards.akka.message;

import java.io.Serializable;

public class SingleSaveResultMessage implements Serializable {
    private final SaveResultStatus resultStatus;

    public SingleSaveResultMessage(SaveResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }

    public SaveResultStatus getResultStatus() {
        return resultStatus;
    }

    @Override
    public String toString() {
        return "SingleSaveResult{" +
                "resultStatus=" + resultStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleSaveResultMessage that = (SingleSaveResultMessage) o;

        return resultStatus == that.resultStatus;

    }

    @Override
    public int hashCode() {
        return resultStatus.hashCode();
    }

    public enum SaveResultStatus {
        SUCCESS,
        FAILURE
    }
}