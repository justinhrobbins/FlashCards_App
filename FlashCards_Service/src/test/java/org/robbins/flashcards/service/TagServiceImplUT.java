
package org.robbins.flashcards.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.TagRepository;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class TagServiceImplUT extends BaseMockingTest {

    @Mock
    private TagRepository repository;

    private TagServiceImpl tagService;

    @Before
    public void before() {
        tagService = new TagServiceImpl();
        ReflectionTestUtils.setField(tagService, "repository", repository);
    }

    @Test
    public void testFindByName() {
        when(repository.findByName(Matchers.anyString())).thenReturn(new Tag("EJB"));

        Tag tag = tagService.findByName("EJB");

        Mockito.verify(repository, Mockito.times(1)).findByName("EJB");
        assertThat(tag, is(Tag.class));
    }
}
