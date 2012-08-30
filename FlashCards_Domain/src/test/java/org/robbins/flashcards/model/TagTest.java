package org.robbins.flashcards.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TagTest {

	@Test
	public void testSetTagName(){
		User user = new User();
		user.setId(1L);
		
		Tag t = new Tag ("default name");
		t.setName("test tag name");
		
		assertEquals("test tag name", t.getName());
	}
	
	@Test
	public void testSetTagId(){
		Tag t = new Tag ();
		t.setId(100L);
		
		assertEquals(Long.valueOf(100), t.getId());
	}
}
