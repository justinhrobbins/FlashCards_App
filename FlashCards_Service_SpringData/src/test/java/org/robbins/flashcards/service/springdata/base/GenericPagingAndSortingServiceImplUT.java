
package org.robbins.flashcards.service.springdata.base;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.springdata.TagRepository;
import org.robbins.flashcards.service.springdata.TagServiceImpl;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class GenericPagingAndSortingServiceImplUT extends BaseMockingTest {

    @Mock
    TagRepository repository;

    TagServiceImpl service;

    @Mock
    PageRequest page;

    @Before
    public void before() {
        service = new TagServiceImpl();
        ReflectionTestUtils.setField(service, "repository", repository);
    }

    @Test
    public void findAll() {

        when(repository.findAll(page)).thenReturn(new PageImpl<Tag>(new ArrayList<Tag>()));

        List<Tag> tags = service.findAll(page);

        verify(repository, Mockito.times(1)).findAll(page);
        assertThat(tags, is(List.class));
    }
}