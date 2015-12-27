package org.robbins.load.tester.message;

import java.io.Serializable;

public class SingleTestResult implements Serializable {
    private final Long duration;
    private final String endPointName;
    private final TestResultStatus resultStatus;

    public SingleTestResult(final String endPointName, final Long duration, final TestResultStatus resultStatus) {
        this.duration = duration;
        this.endPointName = endPointName;
        this.resultStatus = resultStatus;
    }

    public Long getDuration() {
        return duration;
    }

    public String getEndPointName() {
        return endPointName;
    }

    public TestResultStatus getResultStatus() {
        return resultStatus;
    }

    @Override
    public String toString() {
        return "LoadTestResult{" +
                "duration=" + duration +
                ", endPointName='" + endPointName + '\'' +
                ", resultStatus=" + resultStatus +
                '}';
    }

    public enum TestResultStatus {
        SUCCESS,
        FAILURE;
    }
}