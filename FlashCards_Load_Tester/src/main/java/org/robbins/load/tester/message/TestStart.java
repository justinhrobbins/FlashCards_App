package org.robbins.load.tester.message;

import java.io.Serializable;

public class TestStart implements Serializable {
    private final String endPointName;
    private final Long testId;

    public TestStart(String endPointName, Long testId) {
        this.endPointName = endPointName;
        this.testId = testId;
    }

    public String getEndPointName() {
        return endPointName;
    }

    public Long getTestId() {
        return testId;
    }

    @Override
    public String toString() {
        return "LoadTest{" +
                "endPointName='" + endPointName + '\'' +
                ", testId=" + testId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestStart testStart = (TestStart) o;

        if (!endPointName.equals(testStart.endPointName)) return false;
        return testId.equals(testStart.testId);

    }

    @Override
    public int hashCode() {
        int result = endPointName.hashCode();
        result = 31 * result + testId.hashCode();
        return result;
    }
}