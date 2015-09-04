package org.robbins.load.tester.service;

import org.robbins.load.tester.message.LoadTestResult;
import org.robbins.load.tester.message.LoadTestStart;

public interface LoadTestingService {

    LoadTestResult doLoadTest(final LoadTestStart testStart) throws Exception;
}
