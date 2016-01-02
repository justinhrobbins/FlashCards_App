
package org.robbins.flashcards.webservices;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.flashcards.client.GenericRestCrudFacade;
import org.robbins.flashcards.client.UserClient;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.dto.builder.UserDtoBuilder;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.tests.webservices.GenericEntityRestTest;
import org.robbins.tests.IntegrationTest;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Category(IntegrationTest.class)
@ContextConfiguration(locations = {"classpath*:META-INF/applicatonContext-client.xml"})
public class UsersResourceIT extends GenericEntityRestTest<UserDto, Long> {

    private static final String OPEN_ID = "Web API Test 'openid'";

    // this entity will be created in @Before and we'll use it for our JUnit tests and
    // then delete it in @After
    private UserDto entity = new UserDtoBuilder().withOpenId(OPEN_ID).withEmail("webapitest@email.com").build();

    @Inject
    private UserClient client;

    @Override
    public void setEntity(final UserDto entity) {
        this.entity = entity;
    }

    @Override
    public UserDto getEntity() {
        return entity;
    }

    @Override
    public GenericRestCrudFacade<UserDto, Long> getClient() {
        return client;
    }

    @Test
    public void testSearchByOpenId() throws FlashCardsException
	{
        final UserDto searchResult = client.findUserByOpenid(OPEN_ID);

        assertTrue(searchResult != null);
    }

    @Test
    public void testUpdateEntity() throws FlashCardsException
    {
        final Long id = getEntity().getId();
        final String UPDATED_VALUE = "updated value";

        final UserDto entity = new UserDto(id);
        entity.setFirstName(UPDATED_VALUE);

        client.update(entity);

        // double-check the Entity info was updated by re-pulling the Entity
        final UserDto retrievedEntity = client.findOne(id);
        assertEquals(UPDATED_VALUE, retrievedEntity.getFirstName());
    }
}
