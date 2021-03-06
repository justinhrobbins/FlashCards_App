
package org.robbins.flashcards.repository.util;

import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class FieldInitializerUtilUT extends BaseMockingTest {

    @Mock
    private EntityManagerFactory mockEntityManagerFactory;

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private PersistenceUnitUtil mockPersistenceUnitUtil;

    private FieldInitializerUtil fieldInitializerUtil;

    private TagDto tagDto;

    private String FIELD_NAME = "name";

    private String COLLECTION_FIELD_NAME = "flashcards";

    @Before
    public void before() {
        fieldInitializerUtil = new FieldInitializerUtil();

        tagDto = new TagDto();
        tagDto.setFlashCards(new HashSet<>());

        when(mockEntityManager.getEntityManagerFactory()).thenReturn(
                mockEntityManagerFactory);
        when(mockEntityManagerFactory.getPersistenceUnitUtil()).thenReturn(
                mockPersistenceUnitUtil);
        when(mockEntityManagerFactory.createEntityManager()).thenReturn(mockEntityManager);

        ReflectionTestUtils.setField(fieldInitializerUtil, "em",
                mockEntityManager);
    }

    @Test
    public void initializeEntity() throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, RepositoryException
	{
        fieldInitializerUtil.initializeEntity(tagDto,
                new HashSet<>(Arrays.asList(COLLECTION_FIELD_NAME)));
    }

    @Test
    public void initializeEntity_collection() throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, RepositoryException {
        List<TagDto> tagList = Arrays.asList(tagDto);

        fieldInitializerUtil.initializeEntity(tagList,
                new HashSet<>(Arrays.asList(FIELD_NAME)));
    }

    @Test
    public void initializeEntity_array() throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, RepositoryException {
        TagDto[] tagArray = { tagDto };

        fieldInitializerUtil.initializeEntity(tagArray,
                new HashSet<>(Arrays.asList(FIELD_NAME)));
    }

    @Test
    public void initializeEntity_nullEntity() throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, RepositoryException {
        tagDto = null;

        fieldInitializerUtil.initializeEntity(tagDto,
                new HashSet<>(Arrays.asList(FIELD_NAME)));
    }
}
