package org.robbins.load.tester.service;

import org.robbins.flashcards.client.GenericRestCrudFacade;
import org.robbins.flashcards.dto.AbstractAuditableDto;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.load.tester.message.LoadTestResult;
import org.robbins.load.tester.message.LoadTestStart;
import org.robbins.load.tester.message.SingleTestResult;
import org.robbins.load.tester.util.LoadingTestingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named("defaultLoadTestingService")
public class DefaultLoadTestingService implements LoadTestingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultLoadTestingService.class);

    @Resource(name="dtoToClientMap")
    Map<String, GenericRestCrudFacade> clients;

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

    private GenericRestCrudFacade getClient(final Class<? extends AbstractAuditableDto> dtoClass) {
        return clients.get(dtoClass.getSimpleName());
    }

    private LoadTestResult saveItemsInBatches(final LoadTestStart loadTestStartMessage) {
        final GenericRestCrudFacade client = getClient(loadTestStartMessage.getDtoClass());
        final List<List<AbstractAuditableDto>> batches = LoadingTestingUtil.createDtosInBatches(loadTestStartMessage.getTotalLoadCount(), loadTestStartMessage.getBatchSize(), loadTestStartMessage.getDtoClass());
        final List<BatchLoadingReceiptDto> results =  batches.stream()
                .map(batch -> client.save(batch))
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
        final GenericRestCrudFacade client = getClient(loadTestStartMessage.getDtoClass());
        final List<AbstractAuditableDto> dtos = LoadingTestingUtil.createDtos(loadTestStartMessage.getTotalLoadCount(), loadTestStartMessage.getDtoClass());

        final List<SingleTestResult> results = dtos.stream()
                .map(dto -> saveItem(loadTestStartMessage.getEndPointName(), client, dto))
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

    private <D extends AbstractAuditableDto> SingleTestResult saveItem(final String endPointName, final GenericRestCrudFacade client, final D dto) {
        long duration;
        SingleTestResult.TestResultStatus resultStatus = SingleTestResult.TestResultStatus.SUCCESS;

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            client.save(dto);

        } catch (FlashCardsException e) {
            LOGGER.error("Unable to create Dto {}, error: {}", dto.toString(), e.getMessage());
            resultStatus = SingleTestResult.TestResultStatus.FAILURE;
        }

        stopWatch.stop();
        duration = stopWatch.getLastTaskTimeMillis();

        return new SingleTestResult(endPointName, duration, resultStatus);
    }
}
