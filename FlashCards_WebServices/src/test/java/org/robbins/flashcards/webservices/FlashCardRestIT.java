package org.robbins.flashcards.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.util.TestEntityGenerator;
import org.robbins.flashcards.webservices.base.GenericEntityRestTest;
import org.robbins.flashcards.webservices.util.ResourceUrls;
import org.springframework.http.HttpStatus;

public class FlashCardRestIT extends GenericEntityRestTest<FlashCard> {
	static Logger logger = Logger.getLogger(FlashCardRestIT.class);
	
	// this entity will be created in @Before and we'll use it for our JUnit tests and then delete it in @After
	private FlashCard entity = TestEntityGenerator.createFlashCard("Web API Test 'Question'", "Web API Test 'Answer'");
	
	@Override
	public void setEntity(FlashCard entity) {
		this.entity = entity;
	}
	
	@Override
	public FlashCard getEntity() {
		return entity;
	}

	@Override
	public String getEntityListUrl() {
		return getServerAddress() + ResourceUrls.flashCards;
	}
	
	@Override
	public String getEntityUrl() {
		return getServerAddress() + ResourceUrls.flashCard;
	}
	
	@Override
	public String postEntityUrl() {
		return getServerAddress() + ResourceUrls.flashCards;
	}

	@Override
	public String putEntityUrl() {
		return getServerAddress() + ResourceUrls.flashCard;
	}

	@Override
	public String deleteEntityUrl() {
		return getServerAddress() + ResourceUrls.flashCard;
	}

	@Override
	public String searchUrl() {
		return getServerAddress() + ResourceUrls.flashCardsSearch;
	}
	
	@Override
	public Class<FlashCard> getClazz() {
		return FlashCard.class;
	}

	@Override
	public Class<FlashCard[]> getClazzArray() {
		return FlashCard[].class;
	}

	private Map<String, String> setupSearchUriVariables() {
		Map<String, String> searchUriVariables = new HashMap<String, String>();
		
		searchUriVariables.put("question", "");
		searchUriVariables.put("tags", "");
		
		return searchUriVariables;
	}
	
	/**
	 * Test search by facility in.
	 */
	@Test
	public void testSearchByTagsIn() {
		logger.debug("Entering testSearchByTagsIn()");

		Map<String, String> uriVariables = setupSearchUriVariables();
		uriVariables.put("tags", "2,3");
		
		// search result
		FlashCard[] searchResult = searchEntities(searchUrl(), uriVariables, FlashCard[].class);

		// test that our get worked
		assertTrue(searchResult.length > 0);
	}
	
	/**
	 * Execute updateEntity.
	 */
	@Test
	public void testUpdateEntity() {
		logger.debug("Entering  updateEntity()");
		
		Long id = getEntity().getId();
		String updatedValue = "updated value";
		
		FlashCard entity = new FlashCard();
		entity.setQuestion(updatedValue);

		// build the URL
		String apiUrl = getServerAddress() + ResourceUrls.flashCardUpdate;
		
		// set the URL parameter
		Map<String, String> vars = Collections.singletonMap("id", String.valueOf(id));
		
		// make the REST call
		HttpStatus status = updateEntity(apiUrl, vars, entity);
		assertEquals(status.toString(), "204");

		// double-check the Entity info was updated by re-pulling the Entity
		FlashCard retrievedEntity = getEntity(getEntityUrl(), getEntity().getId(), getClazz());
		assertEquals(updatedValue, retrievedEntity.getQuestion());
	}
}
