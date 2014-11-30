
package org.robbins.flashcards.repository.jpa;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.TagRepository;
import org.robbins.tests.BaseIntegrationTest;
import org.robbins.tests.IntegrationTest;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@ContextConfiguration("classpath:test-applicationContext-repository-jpa.xml")
@DatabaseSetup("classpath:test-flashCardsAppRepository.xml")
@Category(IntegrationTest.class)
public class TagRepositoryIT extends BaseIntegrationTest {

    @Inject
    private TagRepository tagRepository;

    @Test
    public void findByName_noSuchTag() {
        Tag tag = tagRepository.findByName("DOES_NOT_EXIST");
        assertThat(tag, is(nullValue()));
    }

    @Test
    public void findByName() {
        Tag tag = tagRepository.findByName("tag1");
        assertThat(tag, is(instanceOf(Tag.class)));
    }

    @Test
    public void findByFlashCardsId() {
        List<Tag> tags = tagRepository.findByFlashcards_Id(1L);
        assertThat(tags.size(), is(1));
    }
}
