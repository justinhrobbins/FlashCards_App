
package org.robbins.flashcards.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.tests.UnitTest;

@Category(UnitTest.class)
public class TagUT {

    @Test
    public void testSetTagName() {

        Tag t = new Tag("default name");
        t.setName("test tag name");

        assertEquals("test tag name", t.getName());
    }
}
