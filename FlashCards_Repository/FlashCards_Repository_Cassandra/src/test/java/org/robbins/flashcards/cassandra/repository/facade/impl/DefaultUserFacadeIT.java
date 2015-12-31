
package org.robbins.flashcards.cassandra.repository.facade.impl;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.robbins.flashcards.cassandra.repository.AbstractCassandraIntegrationTest;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.dto.builder.UserDtoBuilder;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.UserFacade;

public class DefaultUserFacadeIT extends AbstractCassandraIntegrationTest {

    private final Long ID = 1L;
    private final Long NON_EXISTING_ID = 2L;
    private final String OPEN_ID = "apiuser";

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

        final UserDto result = userFacade.findOne(NON_EXISTING_ID);
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
                .withOpenId(RandomStringUtils.randomAlphabetic(10)).build();

        return userFacade.save(dto);
    }
}
