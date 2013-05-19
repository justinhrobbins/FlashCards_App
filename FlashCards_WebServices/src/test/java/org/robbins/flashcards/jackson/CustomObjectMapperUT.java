package org.robbins.flashcards.jackson;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.tests.UnitTest;

@Category(UnitTest.class)
public class CustomObjectMapperUT {

	@Test
	public void setPrettyPrint() {
		CustomObjectMapper mapper = new CustomObjectMapper();
		mapper.setPrettyPrint(Boolean.TRUE);
	}
}
