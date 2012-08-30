package org.robbins.flashcards.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.junit.Test;

public class FlashCardTest {
	
	static Logger logger = Logger.getLogger(FlashCardTest.class);

	@Test
	public void testSetQuestion() {
		User user = new User();
		user.setId(1L);		
		
		FlashCard fc = new FlashCard("default question", "default answer");
		fc.setQuestion("Test Question");

		assertEquals("Test Question", fc.getQuestion());
	}

	@Test
	public void testSetAnswer() {
		User user = new User();
		user.setId(1L);
		
		FlashCard fc = new FlashCard("default question", "default answer");
		fc.setAnswer("Test Answer");
		assertEquals("Test Answer", fc.getAnswer());
	}
	
	@Test
	public void testSetLink() {
		User user = new User();
		user.setId(1L);
		
		FlashCard fc = new FlashCard("default question", "default answer");
		fc.getLinks().add("http://twitter.com/");
		assertEquals("http://twitter.com/", fc.getLinks().get(0));		
	}

	@Test
	public void testSetId(){
		FlashCard fc = new FlashCard();
		fc.setId(100L);
		
		assertEquals(Long.valueOf(100), fc.getId());
	}
	
	@Test
	public void testGetTagName() {
		User user = new User();
		user.setId(1L);
		
		Tag t = new Tag ("test tag name");
		FlashCard fc = new FlashCard("default question", "default answer");

		// add the Tag to the FlashCard
		fc.getTags().add(t);
		
		// let's assume we'll fail the test
		Boolean tagExists = false;
		
		// iterate through the Tags in this FlashCard
		Iterator<Tag> iterator = fc.getTags().iterator();
		while (iterator.hasNext()) {
			// get a reference to the current Tag
			Tag tempTag = (Tag)iterator.next();
				
			// does the current Tag's name match our test name?
			if (tempTag.getName().equals("test tag name")) {
				// test succeeded
				tagExists = true;
			}
		}

		assertTrue("'test tag name' not found", tagExists);
	}
}
