package org.robbins.load.tester.service;

import org.robbins.flashcards.client.TagClient;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.dto.builder.TagDtoBuilder;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.load.tester.message.LoadTestResult;
import org.robbins.load.tester.message.LoadTestStart;
import org.robbins.load.tester.message.TestResult;
import org.robbins.load.tester.message.TestStart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;
import java.util.stream.LongStream;

@Named("defaultLoadTestingService")
public class DefaultLoadTestingService implements LoadTestingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultLoadTestingService.class);

    @Inject
    private TagClient tagClient;

    private Long totalDuration = 0L;
    private Long successCount = 0L;
    private Long failureCount = 0L;

    @Override
    public LoadTestResult doLoadTest(LoadTestStart loadTestStartMessage) throws Exception {
        LOGGER.info("Starting load test");
        LongStream.range(1, loadTestStartMessage.getEndPointInvocationCount() + 1)
                .forEach(i -> {

                    TestStart testStartMessage = new TestStart(loadTestStartMessage.getEndPointName(), i);

                    final TagDto tag = new TagDtoBuilder().withName("load-tester-"
                            + UUID.randomUUID().toString() + "-" + testStartMessage.getTestId()).build();
                    TestResult testResult = saveItem(testStartMessage, tag);

                    totalDuration += testResult.getDuration();

                    if (testResult.getResultStatus().equals(TestResult.TestResultStatus.SUCCESS)) {
                        successCount++;
                    } else {
                        failureCount++;
                    }
                });

        LoadTestResult result = new LoadTestResult(loadTestStartMessage.getEndPointInvocationCount(), loadTestStartMessage.getEndPointName(),
                this.successCount, this.failureCount, this.totalDuration);
        LOGGER.info("LoadTestResult: {}", result);
        return result;
    }

    private TestResult saveItem(final TestStart testStartMessage, final TagDto tag) {
        long duration;
        TestResult.TestResultStatus resultStatus = TestResult.TestResultStatus.SUCCESS;

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            tagClient.save(tag);

        } catch (FlashcardsException e) {
            LOGGER.error("Unable to create Tag {}, error: {}", tag.toString(), e.getMessage());
            resultStatus = TestResult.TestResultStatus.FAILURE;
        }

        stopWatch.stop();
        duration = stopWatch.getLastTaskTimeMillis();

        return new TestResult(testStartMessage.getEndPointName(), duration, resultStatus);
    }
}
