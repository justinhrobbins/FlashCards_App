
package org.robbins.flashcards.cassandra.repository.facade.impl;

import org.cassandraunit.spring.CassandraDataSet;
import org.junit.Test;
import org.robbins.flashcards.cassandra.repository.AbstractCassandraIntegrationTest;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.TagFacade;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
@CassandraDataSet(value = {"cql/TagRepositoryIT.cql"}, keyspace = "flashcardsapp")
public class DefaultTagFacadeIT extends AbstractCassandraIntegrationTest {

    @Inject
    private TagFacade tagFacade;

    @Test
    public void list() throws FlashcardsException
	{
        List<TagDto> results = tagFacade.list();
        assertThat(results, is(notNullValue()));
        assertThat(results.size(), is(2));
    }
}
