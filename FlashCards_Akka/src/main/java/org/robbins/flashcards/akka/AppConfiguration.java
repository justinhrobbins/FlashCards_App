package org.robbins.flashcards.akka;


import akka.actor.ActorSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * The application configuration.
 */
@Configuration
@ComponentScan("org.robbins.load.tester")
public class AppConfiguration {

    // the application context is needed to initialize the Akka Spring Extension
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Actor system singleton for this application.
     */
    @Bean
    public ActorSystem actorSystem() {
        ActorSystem system = ActorSystem.create("AkkaJavaSpring");
        // initialize the application context in the Akka Spring Extension
        SpringExtension.SpringExtProvider.get(system).initialize(applicationContext);
        return system;
    }
}
