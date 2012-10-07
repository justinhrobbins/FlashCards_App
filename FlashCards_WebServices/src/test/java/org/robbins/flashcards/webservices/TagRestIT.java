package org.robbins.flashcards.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.model.util.TestEntityGenerator;
import org.robbins.flashcards.webservices.base.GenericEntityRestTest;
import org.robbins.flashcards.webservices.util.ResourceUrls;
import org.springframework.http.HttpStatus;

public class TagRestIT extends GenericEntityRestTest<Tag> {
	static Logger logger = Logger.getLogger(TagRestIT.class);
	
	// this entity will be created in @Before and we'll use it for our JUnit tests and then delete it in @After
	private Tag entity = TestEntityGenerator.createTag("Web API Test 'Tag'");
	
	@Override
	public void setEntity(Tag entity) {
		this.entity = entity;
	}
	
	@Override
	public Tag getEntity() {
		return entity;
	}

	@Override
	public String getEntityListUrl() {
		return getServerAddress() + ResourceUrls.tags;
	}
	
	@Override
	public String getEntityUrl() {
		return getServerAddress() + ResourceUrls.tag;
	}
	
	@Override
	public String postEntityUrl() {
		return getServerAddress() + ResourceUrls.tags;
	}

	@Override
	public String putEntityUrl() {
		return getServerAddress() + ResourceUrls.tag;
	}

	@Override
	public String deleteEntityUrl() {
		return getServerAddress() + ResourceUrls.tag;
	}

	@Override
	public String searchUrl() {
		return getServerAddress() + ResourceUrls.tagsSearch;
	}
	
	@Override
	public Class<Tag> getClazz() {
		return Tag.class;
	}

	@Override
	public Class<Tag[]> getClazzArray() {
		return Tag[].class;
	}
	
	/**
	 * Test search by facility in.
	 */
	@Test
	public void testSearchByName() {
		logger.debug("Entering testSearchByName()");
		
		Map<String, String> uriVariables = new HashMap<String, String>();

		String name = "EJB";
		uriVariables.put("name", name);
		
		// search results
		Tag searchResult = searchSingleEntity(searchUrl(), uriVariables, Tag.class);

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
		
		Tag entity = new Tag();
		entity.setName(updatedValue);

		// build the URL
		String apiUrl = getServerAddress() + ResourceUrls.tagUpdate;
		
		// set the URL parameter
		Map<String, String> vars = Collections.singletonMap("id", String.valueOf(id));
		
		// make the REST call
		HttpStatus status = updateEntity(apiUrl, vars, entity);
		assertEquals(status.toString(), "204");

		// double-check the Entity info was updated by re-pulling the Entity
		Tag retrievedEntity = getEntity(getEntityUrl(), getEntity().getId(), getClazz());
		assertEquals(updatedValue, retrievedEntity.getName());
	}
}
