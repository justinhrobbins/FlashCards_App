package org.robbins.flashcards.repository.springdata;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.flashcards.model.Tag;
import org.robbins.tests.BaseIntegrationTest;
import org.robbins.tests.IntegrationTest;
import org.springframework.test.context.ContextConfiguration;

import com.github.springtestdbunit.annotation.DatabaseSetup;

@ContextConfiguration("classpath:test-applicationContext-service-springdata.xml")
@DatabaseSetup("classpath:test-flashCardsAppRepository.xml")
@Category(IntegrationTest.class)
public class TagRepositoryIT extends BaseIntegrationTest {

	@Inject
	TagRepository tagRepository;

	@Test
	public void findByName_noSuchTag(){
		Tag tag = tagRepository.findByName("DOES_NOT_EXIST"); 
		assertThat(tag, is(nullValue()));
	}

	@Test
	public void findByName() {
		Tag tag = tagRepository.findByName("tag1"); 
		assertThat(tag, is(instanceOf(Tag.class)));
	}
}
