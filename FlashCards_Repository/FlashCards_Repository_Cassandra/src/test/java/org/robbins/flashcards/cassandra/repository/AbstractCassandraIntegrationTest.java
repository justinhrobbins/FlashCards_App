package org.robbins.flashcards.cassandra.repository;

import javax.inject.Inject;

import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robbins.flashcards.cassandra.repository.util.IntegrationTestUtils;
import org.robbins.tests.IntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/test-applicationContext-repository-cassandra.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class })
@Category(IntegrationTest.class)
public abstract class AbstractCassandraIntegrationTest
{

    @Inject
    public IntegrationTestUtils testUtils;
}
