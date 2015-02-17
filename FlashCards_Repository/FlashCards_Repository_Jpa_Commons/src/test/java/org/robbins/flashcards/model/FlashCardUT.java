
package org.robbins.flashcards.model;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.tests.UnitTest;

import java.util.Iterator;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Category(UnitTest.class)
public class FlashCardUT {

    private final String FLASHCARD_ID = UUID.randomUUID().toString();

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
    public void testSetId() {
        FlashCard fc = new FlashCard();
        fc.setId(FLASHCARD_ID);

        assertEquals(FLASHCARD_ID, fc.getId());
    }

    @Test
    public void testGetTagName() {

        Tag tag = new Tag();
        tag.setName("test tag name");
        FlashCard fc = new FlashCard("default question", "default answer");

        // add the Tag to the FlashCard
        fc.getTags().add(tag);

        // let's assume we'll fail the test
        Boolean tagExists = false;

        // iterate through the Tags in this FlashCard
        Iterator<Tag> iterator = fc.getTags().iterator();
        while (iterator.hasNext()) {
            // get a reference to the current Tag
            Tag tempTag = iterator.next();

            // does the current Tag's name match our test name?
            if (tempTag.getName().equals("test tag name")) {
                // test succeeded
                tagExists = true;
            }
        }

        assertTrue("'test tag name' not found", tagExists);
    }
}
