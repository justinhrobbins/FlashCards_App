
package org.robbins.flashcards.repository.facade.impl;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.tests.BaseIntegrationTest;
import org.robbins.tests.IntegrationTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@ContextConfiguration({ "classpath:applicationContext-repository-jpa-commons.xml",
    "classpath:test-applicationContext-repository-jpa-commons.xml" })
@DatabaseSetup("classpath:test-flashCardsAppRepository.xml")
@Profile("spring-data")
@Category(IntegrationTest.class)
@Ignore
public class DefaultTagFacadeIT extends BaseIntegrationTest {

    @Inject
    private TagFacade tagFacade;

    private static String TAG_NAME = "tag1";

    @Test
    public void findByName() throws FlashcardsException
	{
        TagDto result = tagFacade.findByName(TAG_NAME);
        assertThat(result, is(TagDto.class));
        assertThat(result.getName(), is(TAG_NAME));
    }
}
