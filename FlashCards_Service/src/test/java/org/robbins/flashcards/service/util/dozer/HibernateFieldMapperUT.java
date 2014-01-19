
package org.robbins.flashcards.service.util.dozer;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.collection.internal.PersistentSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.tests.BaseMockingTest;

public class HibernateFieldMapperUT extends BaseMockingTest {

    private HibernateFieldMapper hibernateFieldMapper;

    @Mock
    private TagDto mockTagDto;

    @Mock
    private PersistentSet mockPersistentSet;

    @Before
    public void before() {
        hibernateFieldMapper = new HibernateFieldMapper();
    }

    @Test
    public void mapField_NotPersistentSet() {

        boolean result = hibernateFieldMapper.mapField(null, null, new String("test"),
                null, null);

        assertThat(result, is(false));
    }

    @Test
    public void mapField_PersistentSetNotInitialized() {
        Set<TagDto> mockTagDtos = new HashSet<TagDto>(Arrays.asList(mockTagDto));
        PersistentSet persistentSet = new PersistentSet(null, mockTagDtos);

        boolean result = hibernateFieldMapper.mapField(null, null, persistentSet, null,
                null);

        assertThat(result, is(false));
    }
}
