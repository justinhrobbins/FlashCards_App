
package org.robbins.flashcards.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.tests.UnitTest;

@Category(UnitTest.class)
public class UserUT {

    @Test
    public void testSetCountry() {
        User user = new User();
        user.setCountry("USA");

        assertEquals("USA", user.getCountry());
    }

    @Test
    public void testSetEmail() {
        User user = new User();
        user.setEmail("justinhrobbins@gmail.com");

        assertEquals("justinhrobbins@gmail.com", user.getEmail());
    }

    @Test
    public void testSetFirstName() {
        User user = new User();
        user.setFirstName("Justin");

        assertEquals("Justin", user.getFirstName());
    }

    @Test
    public void testSetFullName() {
        User user = new User();
        user.setFullName("Justin H Robbins");

        assertEquals("Justin H Robbins", user.getFullName());
    }

    @Test
    public void testSetLanguage() {
        User user = new User();
        user.setLanguage("en-US");

        assertEquals("en-US", user.getLanguage());
    }

    @Test
    public void testSetLastName() {
        User user = new User();
        user.setLastName("Robbins");

        assertEquals("Robbins", user.getLastName());
    }

    @Test
    public void testSetNickname() {
        User user = new User();
        user.setNickname("Jenson");

        assertEquals("Jenson", user.getNickname());
    }

    @Test
    public void testSetOpenid() {
        User user = new User();
        user.setOpenid("https://www.google.com/accounts/o8/id?id=AItOawmBkdEwGU7NSuq-vNPDWq8Nu4sAsSC55KE");

        assertEquals(
                "https://www.google.com/accounts/o8/id?id=AItOawmBkdEwGU7NSuq-vNPDWq8Nu4sAsSC55KE",
                user.getOpenid());
    }

    @Test
    public void testSetUserId() {
        User user = new User(1L);

        assertEquals(new Long(1), user.getId());
    }
}
