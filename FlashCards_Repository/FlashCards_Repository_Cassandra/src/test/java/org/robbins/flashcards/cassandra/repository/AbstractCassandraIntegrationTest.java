package org.robbins.flashcards.cassandra.repository;

import javax.inject.Inject;

import org.cassandraunit.spring.CassandraDataSet;

import org.cassandraunit.spring.EmbeddedCassandra;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robbins.flashcards.cassandra.repository.executionlisteners.CassandraUnitDependencyInjectionTestExecutionListener;
import org.robbins.flashcards.cassandra.repository.util.IntegrationTestUtils;
import org.robbins.tests.IntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/test-applicationContext-repository-cassandra.xml")
@TestExecutionListeners({CassandraUnitDependencyInjectionTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class})
@EmbeddedCassandra(configuration = "flashcards-cassandra.yaml")
@CassandraDataSet(value = {"cql/FlashCard.cql", "cql/Tag.cql", "cql/User.cql", "cql/Tag_FlashCard.cql"}, keyspace = "flashcardsapp")
@Category(IntegrationTest.class)
public abstract class AbstractCassandraIntegrationTest {

    @Inject
    public IntegrationTestUtils testUtils;
}
