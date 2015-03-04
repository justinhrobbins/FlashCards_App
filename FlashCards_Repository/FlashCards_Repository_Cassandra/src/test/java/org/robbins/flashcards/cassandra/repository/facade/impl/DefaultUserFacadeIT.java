
package org.robbins.flashcards.cassandra.repository.facade.impl;

import org.apache.commons.lang.RandomStringUtils;
import org.cassandraunit.spring.CassandraDataSet;
import org.junit.Test;
import org.robbins.flashcards.cassandra.repository.AbstractCassandraIntegrationTest;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.dto.UserDtoBuilder;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.UserFacade;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@CassandraDataSet(value = {"cql/UserRepositoryIT.cql"}, keyspace = "flashcardsapp")
public class DefaultUserFacadeIT extends AbstractCassandraIntegrationTest {

    private final String ID = "9caa6c8e-b720-11e4-a71e-12e3f512a338";
    private final String OPEN_ID = "api_user_open_id";

    @Inject
    private UserFacade userFacade;

    @Test
    public void testList() throws FlashcardsException
	{
        final List<UserDto> results = userFacade.list();

        assertThat(results, is(notNullValue()));
        assertThat(results.size(), greaterThan(0));
    }

    @Test
    public void testFindOne() throws FlashcardsException {

        final UserDto result = userFacade.findOne(ID);
        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), is(ID));
    }

    @Test
    public void testFindOne_ShouldReturnNull_WhenIdDoesNotExist() throws FlashcardsException {

        final UserDto result = userFacade.findOne(UUID.randomUUID().toString());
        assertThat(result, is(nullValue()));
    }

    @Test
    public void testFindByOpenId() throws FlashcardsException {

        final UserDto result = userFacade.findUserByOpenid(OPEN_ID);
        assertThat(result.getOpenid(), is(OPEN_ID));
    }

    @Test
    public void testFindByOpenId_ShouldReturnNull_WhenIdDoesNotExist() throws FlashcardsException {

        final UserDto result = userFacade.findUserByOpenid("open id does not exist");
        assertThat(result, is(nullValue()));
    }

    @Test
    public void testSave() throws FlashcardsException
    {
        final String OPEN_ID = "new open id";
        final UserDto dto = new UserDtoBuilder()
                .withOpenId(OPEN_ID).build();
        final UserDto result =  userFacade.save(dto);

        assertThat(result, is(notNullValue()));
        assertThat(result.getOpenid(), is(OPEN_ID));
    }

    @Test
    public void testUpdate() throws FlashcardsException
    {
        final String UPDATED_OPEN_ID = "updated open id";
        final UserDto dtoToUpdate = createUser();
        dtoToUpdate.setOpenid(UPDATED_OPEN_ID);

        final UserDto result =  userFacade.save(dtoToUpdate);

        assertThat(result, is(notNullValue()));
        assertThat(result.getOpenid(), is(UPDATED_OPEN_ID));
    }

    @Test
    public void testDelete() throws FlashcardsException {
        final UserDto dtoToDelete = createUser();

        userFacade.delete(dtoToDelete.getId());
    }

    private UserDto createUser() throws FlashcardsException {
        final UserDto dto = new UserDtoBuilder()
                .withOpenId(RandomStringUtils.random(10)).build();

        return userFacade.save(dto);
    }
}
