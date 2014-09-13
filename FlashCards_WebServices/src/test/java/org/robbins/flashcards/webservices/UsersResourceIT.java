
package org.robbins.flashcards.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.tests.webservices.GenericEntityRestTest;
import org.robbins.flashcards.util.TestDtoGenerator;
import org.robbins.flashcards.webservices.util.ResourceUrls;
import org.robbins.tests.IntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

@Category(IntegrationTest.class)
@ContextConfiguration(locations = { "classpath*:applicatonContext-webServices-test.xml" })
public class UsersResourceIT extends GenericEntityRestTest<UserDto> {

    // this entity will be created in @Before and we'll use it for our JUnit tests and
    // then delete it in @After
    private UserDto entity = TestDtoGenerator.createUserDto("Web API Test 'openid'",
            "webapitest@email.com");

    @Override
    public void setEntity(final UserDto entity) {
        this.entity = entity;
    }

    @Override
    public UserDto getEntity() {
        return entity;
    }

    @Override
    public String getEntityListUrl() {
        return getServerAddress() + ResourceUrls.users;
    }

    @Override
    public String getEntityUrl() {
        return getServerAddress() + ResourceUrls.user;
    }

    @Override
    public String postEntityUrl() {
        return getServerAddress() + ResourceUrls.users;
    }

    @Override
    public String putEntityUrl() {
        return getServerAddress() + ResourceUrls.user;
    }

    @Override
    public String deleteEntityUrl() {
        return getServerAddress() + ResourceUrls.user;
    }

    @Override
    public String searchUrl() {
        return getServerAddress() + ResourceUrls.usersSearch;
    }

    @Override
    public Class<UserDto> getClazz() {
        return UserDto.class;
    }

    @Override
    public Class<UserDto[]> getClazzArray() {
        return UserDto[].class;
    }

    /**
     * Test search by facility in.
     */
    @Test
    public void testSearchByOpenId() {
        Map<String, String> uriVariables = new HashMap<String, String>();

        String openid = "Web API Test 'openid'";
        uriVariables.put("openid", openid);

        // search results
        UserDto searchResult = searchSingleEntity(searchUrl(), uriVariables,
                UserDto.class);

        // test that our get worked
        assertTrue(searchResult != null);
    }

    /**
     * Execute updateEntity.
     */
    @Test
    public void testUpdateEntity() {
        Long id = getEntity().getId();
        String updatedValue = "updated value";

        UserDto entity = new UserDto();
        entity.setFirstName(updatedValue);

        // build the URL
        String apiUrl = getServerAddress() + ResourceUrls.userUpdate;

        // set the URL parameter
        Map<String, String> vars = Collections.singletonMap("id", String.valueOf(id));

        // make the REST call
        HttpStatus status = updateEntity(apiUrl, vars, entity);
        assertEquals(status.toString(), "204");

        // double-check the Entity info was updated by re-pulling the Entity
        UserDto retrievedEntity = getEntity(getEntityUrl(), getEntity().getId(),
                getClazz());
        assertEquals(updatedValue, retrievedEntity.getFirstName());
    }
}
