package org.robbins.load.tester;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.robbins.flashcards.dto.AbstractAuditableDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.load.tester.message.LoadTestResult;
import org.robbins.load.tester.message.LoadTestStart;
import org.robbins.load.tester.service.LoadTestingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.util.StopWatch;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@Ignore
@ContextConfiguration(locations = {"classpath*:META-INF/applicatonContext-loadtester.xml"})
@RunWith(JUnitParamsRunner.class)
public class LoadTesterIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadTesterIT.class);

    private TestContextManager testContextManager;
    private final Integer totalLoadCount = 5000000;
    private final Integer batchSize = 10000;
//    private final Integer totalLoadCount = 10;
//    private final Integer batchSize = 10;
    private final String endPointName = "tagClient";
    private StopWatch stopWatch;

    @Qualifier("akkaLoadTestingService")
    @Inject
    private LoadTestingService akkaLoadTestingService;

    @Qualifier("defaultLoadTestingService")
    @Inject
    private LoadTestingService defaultLoadTestingService;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void before() throws Exception {
        this.testContextManager = new TestContextManager(getClass());
        this.testContextManager.prepareTestInstance(this);

        stopWatch = new StopWatch();
        stopWatch.start();
    }

    @After
    public void after() {
        stopWatch.stop();
        LOGGER.info("Test '{}' duration: {} seconds", testName.getMethodName(), Math.ceil(stopWatch.getLastTaskTimeMillis() / 1000));
    }

    private Object[] testParams() {
        return new Object[]{
                new Object[]{TagDto.class}
//                , new Object[]{TagDto.class}
//                , new Object[]{TagDto.class}
//                , new Object[]{TagDto.class}
//                , new Object[]{TagDto.class}
        };
    }

    @Test
    @Parameters(method = "testParams")
    public void testAkkaLoadTest(final Class<? extends AbstractAuditableDto> dtoClass) throws Exception {

        final LoadTestResult result = akkaLoadTestingService.doLoadTest(new LoadTestStart(endPointName, totalLoadCount, 1, dtoClass));
        Assert.assertEquals(totalLoadCount, result.getGetTotalLoadCount());
    }

    @Test
    @Parameters(method = "testParams")
    public void testDefaultLoadTest(final Class<? extends AbstractAuditableDto> dtoClass) throws Exception {

        final LoadTestResult result = defaultLoadTestingService.doLoadTest(new LoadTestStart(endPointName, totalLoadCount, 1, dtoClass));
        Assert.assertEquals(totalLoadCount, result.getGetTotalLoadCount());
    }

    @Test
    @Parameters(method = "testParams")
    public void testDefaultLoadTestInBatch(final Class<? extends AbstractAuditableDto> dtoClass) throws Exception {

        final LoadTestResult result = defaultLoadTestingService.doLoadTest(new LoadTestStart(endPointName, totalLoadCount, batchSize, dtoClass));
        Assert.assertEquals(totalLoadCount, result.getGetTotalLoadCount());
    }

    @Test
    @Parameters(method = "testParams")
    public void testAkkaLoadTestInBatch(final Class<? extends AbstractAuditableDto> dtoClass) throws Exception {

        final LoadTestResult result = akkaLoadTestingService.doLoadTest(new LoadTestStart(endPointName, totalLoadCount, batchSize, dtoClass));
        Assert.assertEquals(totalLoadCount, result.getGetTotalLoadCount());
    }
}
