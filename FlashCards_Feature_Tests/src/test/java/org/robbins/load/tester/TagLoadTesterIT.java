package org.robbins.load.tester;

import akka.actor.ActorSystem;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.robbins.load.tester.message.LoadTestResult;
import org.robbins.load.tester.message.LoadTestStart;
import org.robbins.load.tester.service.LoadTestingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;

import javax.inject.Inject;

@ContextConfiguration(locations = {"classpath*:test-applicatonContext-loadTester.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TagLoadTesterIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagLoadTesterIT.class);

    private final Integer totalLoadCount = 10000;
    private final Integer batchSize = 1000;
    private final String ENDPOINT_NAME = "tagClient";
    private StopWatch stopWatch;

    @Qualifier("akkaLoadTestingService")
    @Inject
    private LoadTestingService akkaLoadTestingService;

    @Qualifier("defaultLoadTestingService")
    @Inject
    private LoadTestingService defaultLoadTestingService;

    @Inject
    private ActorSystem system;

    @Rule public TestName testName = new TestName();

    @Before
    public void before() {
        stopWatch = new StopWatch();
        stopWatch.start();
    }

    @After
    public void after() {
        stopWatch.stop();
        LOGGER.info("Test '{}' duration: {} seconds", testName.getMethodName(), Math.ceil(stopWatch.getLastTaskTimeMillis() / 1000));
    }

    @Test
    public void testAkkaLoadTest() throws Exception {

        final LoadTestResult result = akkaLoadTestingService.doLoadTest(new LoadTestStart(totalLoadCount, 1, ENDPOINT_NAME));
        Assert.assertEquals(totalLoadCount, result.getGetTotalLoadCount());
    }

    @Test
    public void testDefaultLoadTest() throws Exception {

        final LoadTestResult result = defaultLoadTestingService.doLoadTest(new LoadTestStart(totalLoadCount, 1, ENDPOINT_NAME));
        Assert.assertEquals(totalLoadCount, result.getGetTotalLoadCount());
    }

    @Test
    public void testDefaultLoadTestInBatch() throws Exception {

        final LoadTestResult result = defaultLoadTestingService.doLoadTest(new LoadTestStart(totalLoadCount, batchSize, ENDPOINT_NAME));
        Assert.assertEquals(totalLoadCount, result.getGetTotalLoadCount());
    }

    @Test
    public void testAkkaLoadTestInBatch() throws Exception {

        final LoadTestResult result = akkaLoadTestingService.doLoadTest(new LoadTestStart(totalLoadCount, batchSize, ENDPOINT_NAME));
        Assert.assertEquals(totalLoadCount, result.getGetTotalLoadCount());
    }
}
