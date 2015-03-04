package org.robbins.flashcards.cassandra.repository;

import org.cassandraunit.spring.CassandraUnitDependencyInjectionTestExecutionListener;
import org.cassandraunit.spring.EmbeddedCassandra;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robbins.tests.IntegrationTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

/**
 * Created by justinrobbins on 2/14/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-repository-cassandra.xml")
@TestExecutionListeners({CassandraUnitDependencyInjectionTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@EmbeddedCassandra(configuration = "flashcards-cassandra.yaml", clusterName = "Flashcards Cluster", host = "127.0.0.1")
@Category(IntegrationTest.class)
public class AbstractCassandraIntegrationTest {
}
