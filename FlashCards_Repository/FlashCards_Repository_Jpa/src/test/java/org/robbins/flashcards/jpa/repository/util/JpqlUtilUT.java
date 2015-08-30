package org.robbins.flashcards.jpa.repository.util;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.tests.UnitTest;
import org.springframework.data.domain.Sort;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Category(UnitTest.class)
public class JpqlUtilUT {

    @Test
    public void testSortAsString() {
        final String property = "MyProperty";
        final Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, property));

        final String result = JpqlUtil.sortAsString(sort);

        assertThat(result, is(property + " " + Sort.Direction.DESC));
    }

    @Test
    public void testSortAsString_DefaultSortToASCWhenDirectionNotProvided() {
        final String property = "MyProperty";
        final Sort sort = new Sort(new Sort.Order( property));

        final String result = JpqlUtil.sortAsString(sort);

        assertThat(result, is(property + " " + Sort.Direction.ASC));
    }

    @Test
    public void testSortAsString_CanSortMultipleProperties() {
        final String property1 = "MyProperty1";
        final String property2 = "MyProperty2";
        final Sort sort = new Sort(new Sort.Order(property1), new Sort.Order((property2)));

        final String result = JpqlUtil.sortAsString(sort);

        assertThat(result, is(property1 + " " + Sort.Direction.ASC + ", " + property2 + " " +Sort.Direction.ASC));
    }
}
