
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
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class TagServiceImplUT extends BaseMockingTest {

    @Mock
    private TagFacade facade;

    private TagServiceImpl tagService;

    @Before
    public void before() {
        tagService = new TagServiceImpl();
        ReflectionTestUtils.setField(tagService, "facade", facade);
    }

    @Test
    public void testFindByName() throws FlashcardsException {
        when(facade.findByName(Matchers.anyString())).thenReturn(new TagDto("EJB"));

        TagDto tag = tagService.findByName("EJB");

        Mockito.verify(facade, Mockito.times(1)).findByName("EJB");
        assertThat(tag, is(TagDto.class));
    }
}
