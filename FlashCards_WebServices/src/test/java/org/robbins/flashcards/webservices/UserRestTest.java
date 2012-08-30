package org.robbins.flashcards.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.model.util.TestEntityGenerator;
import org.robbins.flashcards.webservices.util.ResourceUrls;
import org.springframework.http.HttpStatus;

public class UserRestTest extends GenericEntityRestTest<User> {
	static Logger logger = Logger.getLogger(UserRestTest.class);
	
	// this entity will be created in @Before and we'll use it for our JUnit tests and then delete it in @After
	private User entity = TestEntityGenerator.createUser("Web API Test 'openid'", "webapitest@email.com");
	
	@Override
	public void setEntity(User entity) {
		this.entity = entity;
	}
	
	@Override
	public User getEntity() {
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
	public Class<User> getClazz() {
		return User.class;
	}

	@Override
	public Class<User[]> getClazzArray() {
		return User[].class;
	}
	
	/**
	 * Test search by facility in.
	 */
	@Test
	public void testSearchByOpenId() {
		logger.debug("Entering testSearchByName()");
		
		Map<String, String> uriVariables = new HashMap<String, String>();

		String openid = "Web API Test 'openid'";
		uriVariables.put("openid", openid);
		
		// search results
		User searchResult = searchSingleEntity(searchUrl(), uriVariables, User.class);

		// test that our get worked
		assertTrue(searchResult != null);
	}
	
	/**
	 * Execute updateEntity.
	 */
	@Test
	public void testUpdateEntity() {
		logger.debug("Entering  updateEntity()");
		
		Long id = getEntity().getId();
		String updatedValue = "updated value";
		
		User entity = new User();
		entity.setFirstName(updatedValue);

		// build the URL
		String apiUrl = getServerAddress() + ResourceUrls.userUpdate;
		
		// set the URL parameter
		Map<String, String> vars = Collections.singletonMap("id", String.valueOf(id));
		
		// make the REST call
		HttpStatus status = updateEntity(apiUrl, vars, entity);
		assertEquals(status.toString(), "204");

		// double-check the Entity info was updated by re-pulling the Entity
		User retrievedEntity = getEntity(getEntityUrl(), getEntity().getId(), getClazz());
		assertEquals(updatedValue, retrievedEntity.getFirstName());
	}
}
