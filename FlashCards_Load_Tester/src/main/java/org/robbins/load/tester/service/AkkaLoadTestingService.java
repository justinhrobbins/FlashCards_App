package org.robbins.load.tester.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.japi.Util;
import akka.util.Timeout;
import org.robbins.flashcards.client.TagClient;
import org.robbins.load.tester.message.LoadTestResult;
import org.robbins.load.tester.message.LoadTestStart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ClassTag;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;
import static org.robbins.load.tester.SpringExtension.SpringExtProvider;

@Named("akkaLoadTestingService")
public class AkkaLoadTestingService implements LoadTestingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AkkaLoadTestingService.class);

    @Inject
    private TagClient tagClient;

    @Inject
    private ActorSystem system;

    @Override
    public LoadTestResult doLoadTest(LoadTestStart testStart) throws Exception {
        LOGGER.info("Sending StartLoadTest message to LoadTestingCoordinator");

        ActorRef loadTestingCoordinator = system.actorOf(
                SpringExtProvider.get(system).props("loadTestingCoordinator"), "load-testing-coordinator");

        FiniteDuration duration = FiniteDuration.create(1, TimeUnit.HOURS);

        ClassTag<LoadTestResult> classTag = Util.classTag(LoadTestResult.class);
        Future<LoadTestResult> resultFuture = ask(loadTestingCoordinator, testStart,
                Timeout.durationToTimeout(duration))
                .mapTo(classTag);

        LoadTestResult result = Await.result(resultFuture, duration);
        shutdownSystem(loadTestingCoordinator);
        return result;
    }

    private void shutdownSystem(final ActorRef loadTestingCoordinator) {
        system.stop(loadTestingCoordinator);
        system.shutdown();
        system.awaitTermination();
    }
}
