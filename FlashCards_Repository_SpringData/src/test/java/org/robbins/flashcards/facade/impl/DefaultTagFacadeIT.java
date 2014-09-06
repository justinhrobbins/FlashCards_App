
package org.robbins.flashcards.facade.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.tests.BaseIntegrationTest;
import org.robbins.tests.IntegrationTest;
import org.springframework.test.context.ContextConfiguration;

import com.github.springtestdbunit.annotation.DatabaseSetup;

@Ignore
@ContextConfiguration({ "classpath:applicationContext-service.xml",
    "classpath:test-applicationContext-service-springdata.xml" })
@DatabaseSetup("classpath:test-flashCardsAppRepository.xml")
@Category(IntegrationTest.class)
public class DefaultTagFacadeIT extends BaseIntegrationTest {

    @Inject
    private TagFacade tagFacade;

    private static String TAG_NAME = "tag1";

    @Test
    public void findByName() {
        TagDto result = tagFacade.findByName(TAG_NAME);
        assertThat(result, is(TagDto.class));
        assertThat(result.getName(), is(TAG_NAME));
    }
}
