package org.robbins.load.tester;

import akka.actor.ActorSystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

import static org.junit.Assert.assertEquals;

@ContextConfiguration(locations = {"classpath*:test-applicatonContext-loadTester.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TagLoadTesterIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagLoadTesterIT.class);

    private final Long numberOfRequests = 100000L;
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

    @Before
    public void before() {
        stopWatch = new StopWatch();
        stopWatch.start();
    }

    @After
    public void after() {
        stopWatch.stop();
        LOGGER.info("Test duration: {} seconds",  Math.ceil(stopWatch.getLastTaskTimeMillis()/1000));
    }

    @Test
    public void testAkkaLoadTest() throws Exception {

        LoadTestResult result = akkaLoadTestingService.doLoadTest(new LoadTestStart(numberOfRequests, ENDPOINT_NAME));
        assertEquals(numberOfRequests, result.getEndPointInvocationCount());
    }

    @Test
    public void testDefaultLoadTest() throws Exception {

        LoadTestResult result = defaultLoadTestingService.doLoadTest(new LoadTestStart(numberOfRequests, ENDPOINT_NAME));
        assertEquals(numberOfRequests, result.getEndPointInvocationCount());
    }
}
