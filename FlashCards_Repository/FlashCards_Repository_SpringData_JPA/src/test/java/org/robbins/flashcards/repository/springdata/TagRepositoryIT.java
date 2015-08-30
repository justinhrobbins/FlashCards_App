
package org.robbins.flashcards.repository.springdata;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.TagRepository;
import org.robbins.tests.BaseIntegrationTest;
import org.robbins.tests.IntegrationTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@ContextConfiguration("classpath:test-applicationContext-repository-springdata.xml")
@DatabaseSetup("classpath:test-flashCardsAppRepository.xml")
@Category(IntegrationTest.class)
public class TagRepositoryIT extends BaseIntegrationTest {

    @Inject
    private TagRepository<Tag, String> tagRepository;

    @Test
    public void findByName() {
        final Tag tag = tagRepository.findByName("tag1");
        assertThat(tag, is(instanceOf(Tag.class)));
    }

    @Test
    public void findById() {
        final String uuid = findFirstTagId();
        final Tag tag = tagRepository.findOne(uuid);
        assertThat(tag, is(instanceOf(Tag.class)));
        assertThat(tag.getId(), is(uuid));
    }

    @Test
    public void findByFlashCardsId() {
        final String flashCardId = findFirstFlashCardId();
        final List<Tag> tags = tagRepository.findByFlashcards_Id(flashCardId);
        assertThat(tags.size(), is(1));
    }

    private String findFirstTagId() {
        return tagRepository.findAll(new PageRequest(0, 1)).iterator().next().getId();
    }

    private String findFirstFlashCardId() {
        List<Tag> tags = tagRepository.findAll();

        Optional<Tag> firstTag = tags.stream()
                .filter(tag -> tag.getFlashcards().size() > 0)
                .findFirst();

        return firstTag.isPresent() ? firstTag.get().getFlashcards().iterator().next().getId() : null;
    }
}
