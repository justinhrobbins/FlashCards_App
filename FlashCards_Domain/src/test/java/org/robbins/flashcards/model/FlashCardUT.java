package org.robbins.flashcards.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.tests.UnitTest;

@Category(UnitTest.class)
public class FlashCardUT {
	
	static Logger logger = Logger.getLogger(FlashCardUT.class);

	@Test
	public void testSetQuestion() {
		
		FlashCard fc = new FlashCard("default question", "default answer");
		fc.setQuestion("Test Question");

		assertEquals("Test Question", fc.getQuestion());
	}

	@Test
	public void testSetAnswer() {
	
		FlashCard fc = new FlashCard("default question", "default answer");
		fc.setAnswer("Test Answer");
		assertEquals("Test Answer", fc.getAnswer());
	}
	
	@Test
	public void testSetLink() {
		
		FlashCard fc = new FlashCard("default question", "default answer");
		fc.getLinks().add("http://twitter.com/");
		assertEquals("http://twitter.com/", fc.getLinks().get(0));		
	}

	@Test
	public void testSetId(){
		FlashCard fc = new FlashCard(100L);
		
		assertEquals(new Long(100), fc.getId());
	}
	
	@Test
	public void testGetTagName() {
		
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
