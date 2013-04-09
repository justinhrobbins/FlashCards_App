package org.robbins.flashcards.repository.springdata;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Test;
import org.robbins.flashcards.model.Tag;
import org.robbins.tests.BaseIntegrationTest;

import com.github.springtestdbunit.annotation.DatabaseSetup;

@DatabaseSetup("flashCardsAppRepository.xml")
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
