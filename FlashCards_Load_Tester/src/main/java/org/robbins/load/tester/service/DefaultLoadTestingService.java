package org.robbins.load.tester.service;

import org.robbins.flashcards.client.TagClient;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.load.tester.message.LoadTestResult;
import org.robbins.load.tester.message.LoadTestStart;
import org.robbins.load.tester.message.SingleTestResult;
import org.robbins.load.tester.util.LoadingTestingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named("defaultLoadTestingService")
public class DefaultLoadTestingService implements LoadTestingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultLoadTestingService.class);

    @Inject
    private TagClient tagClient;

    @Override
    public LoadTestResult doLoadTest(final LoadTestStart loadTestStartMessage) throws Exception {
        LOGGER.debug("Starting load test");

        if (loadTestStartMessage.getBatchSize().equals(1)) {
            return saveItemsIndividually(loadTestStartMessage);
        }
        else {
            return saveItemsInBatches(loadTestStartMessage);
        }
    }

    private LoadTestResult saveItemsInBatches(final LoadTestStart loadTestStartMessage) {
        final List<List<TagDto>> batches = LoadingTestingUtil.createTagDtosInBatches(loadTestStartMessage.getTotalLoadCount(), loadTestStartMessage.getBatchSize());
        final List<BatchLoadingReceiptDto> results =  batches.stream()
                .map(tagClient::save)
                .collect(Collectors.toList());

        final long totalDuration = results.stream()
                .mapToLong(result -> LoadingTestingUtil.calculateLoadingDuration(result.getStartTime(), result.getEndTime()))
                .sum();

        final int successCount = results.stream()
                .mapToInt(BatchLoadingReceiptDto::getSuccessCount)
                .sum();

        final int failureCount = results.stream()
                .mapToInt(BatchLoadingReceiptDto::getFailureCount)
                .sum();

        LoadTestResult result = new LoadTestResult(loadTestStartMessage.getTotalLoadCount(), loadTestStartMessage.getEndPointName(),
                successCount, failureCount, totalDuration);

        LOGGER.debug("LoadTestResult: {}", result);
        return result;
    }

    private LoadTestResult saveItemsIndividually(final LoadTestStart loadTestStartMessage) {
        final List<TagDto> tags = LoadingTestingUtil.createTagDtos(loadTestStartMessage.getTotalLoadCount());

        final List<SingleTestResult> results = tags.stream()
                .map(tagDto -> saveItem(loadTestStartMessage.getEndPointName(), tagDto))
                .collect(Collectors.toList());

        final long totalDuration = results.stream().collect(Collectors.summingLong(SingleTestResult::getDuration));

        final int successCount = (int) results.stream()
                .filter(result -> result.getResultStatus().equals(SingleTestResult.TestResultStatus.SUCCESS))
                .count();

        final int failureCount = (int) results.stream()
                .filter(result -> result.getResultStatus().equals(SingleTestResult.TestResultStatus.FAILURE))
                .count();

        LoadTestResult result = new LoadTestResult(loadTestStartMessage.getTotalLoadCount(), loadTestStartMessage.getEndPointName(),
                successCount, failureCount, totalDuration);
        LOGGER.debug("LoadTestResult: {}", result);
        return result;
    }

    private SingleTestResult saveItem(final String endPointName, final TagDto tag) {
        long duration;
        SingleTestResult.TestResultStatus resultStatus = SingleTestResult.TestResultStatus.SUCCESS;

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            tagClient.save(tag);

        } catch (FlashcardsException e) {
            LOGGER.error("Unable to create Tag {}, error: {}", tag.toString(), e.getMessage());
            resultStatus = SingleTestResult.TestResultStatus.FAILURE;
        }

        stopWatch.stop();
        duration = stopWatch.getLastTaskTimeMillis();

        return new SingleTestResult(endPointName, duration, resultStatus);
    }
}
