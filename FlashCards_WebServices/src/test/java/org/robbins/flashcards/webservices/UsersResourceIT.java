
package org.robbins.flashcards.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.flashcards.client.GenericRestCrudFacade;
import org.robbins.flashcards.client.UserClient;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.tests.webservices.GenericEntityRestTest;
import org.robbins.flashcards.util.TestDtoGenerator;
import org.robbins.tests.IntegrationTest;
import org.springframework.test.context.ContextConfiguration;

@Category(IntegrationTest.class)
@ContextConfiguration(locations = {"classpath*:applicatonContext-client.xml"})
public class UsersResourceIT extends GenericEntityRestTest<UserDto> {

    private static final String OPEN_ID = "Web API Test 'openid'";

    // this entity will be created in @Before and we'll use it for our JUnit tests and
    // then delete it in @After
    private UserDto entity = TestDtoGenerator.createUserDto(OPEN_ID,
            "webapitest@email.com");

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
    public GenericRestCrudFacade<UserDto> getClient() {
        return client;
    }

    /**
     * Test search by facility in.
     */
    @Test
    public void testSearchByOpenId() throws FlashcardsException
	{
        final UserDto searchResult = client.findUserByOpenid(OPEN_ID);

        assertTrue(searchResult != null);
    }

    /**
     * Execute updateEntity.
     */
    @Test
    public void testUpdateEntity() throws FlashcardsException {
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
