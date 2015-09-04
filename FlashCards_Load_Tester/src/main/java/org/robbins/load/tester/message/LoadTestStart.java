package org.robbins.load.tester.message;

import java.io.Serializable;

public class LoadTestStart implements Serializable {
    private final Long endPointInvocationCount;
    private final String endPointName;

    public LoadTestStart(Long endPointInvocationCount, String endPointName) {
        this.endPointInvocationCount = endPointInvocationCount;
        this.endPointName = endPointName;
    }

    public Long getEndPointInvocationCount() {
        return endPointInvocationCount;
    }

    public String getEndPointName() {
        return endPointName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoadTestStart that = (LoadTestStart) o;

        if (!endPointInvocationCount.equals(that.endPointInvocationCount)) return false;
        return endPointName.equals(that.endPointName);

    }

    @Override
    public int hashCode() {
        int result = endPointInvocationCount.hashCode();
        result = 31 * result + endPointName.hashCode();
        return result;
    }
}
