package org.robbins.load.tester.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.japi.Util;
import akka.util.Timeout;
import org.robbins.flashcards.akka.SpringExtension;
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


@Named("akkaLoadTestingService")
public class AkkaLoadTestingService implements LoadTestingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AkkaLoadTestingService.class);

    @Inject
    private ActorSystem system;

    @Override
    public LoadTestResult doLoadTest(final LoadTestStart testStart) throws Exception {
        LOGGER.debug("Sending StartLoadTest message to LoadTestingCoordinator");

        final ActorRef loadTestingCoordinator = system.actorOf(
                SpringExtension.SpringExtProvider.get(system).props("loadTestingCoordinator"), "load-testing-coordinator");

        final FiniteDuration duration = FiniteDuration.create(1, TimeUnit.HOURS);

        final ClassTag<LoadTestResult> classTag = Util.classTag(LoadTestResult.class);
        final Future<LoadTestResult> resultFuture = ask(loadTestingCoordinator, testStart,
                Timeout.durationToTimeout(duration))
                .mapTo(classTag);

        final LoadTestResult result = Await.result(resultFuture, duration);
        return result;
    }
}
