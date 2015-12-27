package org.robbins.load.tester.message;

import org.robbins.flashcards.dto.AbstractAuditableDto;

import java.io.Serializable;

public class TestStart implements Serializable {
    private final String endPointName;
    private final Long testId;
    private final Class<? extends AbstractAuditableDto> dtoClass;

    public TestStart(final String endPointName, final Long testId, final Class<? extends AbstractAuditableDto> dtoClass) {
        this.endPointName = endPointName;
        this.testId = testId;
        this.dtoClass = dtoClass;
    }

    public String getEndPointName() {
        return endPointName;
    }

    public Long getTestId() {
        return testId;
    }

    public Class<? extends AbstractAuditableDto> getDtoClass() {
        return dtoClass;
    }

    @Override
    public String toString() {
        return "TestStart{" +
                "endPointName='" + endPointName + '\'' +
                ", testId=" + testId +
                ", dtoClass=" + dtoClass +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestStart testStart = (TestStart) o;

        if (!endPointName.equals(testStart.endPointName)) return false;
        if (!testId.equals(testStart.testId)) return false;
        return dtoClass.equals(testStart.dtoClass);

    }

    @Override
    public int hashCode() {
        int result = endPointName.hashCode();
        result = 31 * result + testId.hashCode();
        result = 31 * result + dtoClass.hashCode();
        return result;
    }
}