
package org.robbins.flashcards.service.util.dozer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.data.mapping.model.MappingException;

@Category(UnitTest.class)
public class DateTimeConverterUT extends BaseMockingTest {

    private DateTimeConverter dateTimeConverter;

    @Before
    public void before() {
        dateTimeConverter = new DateTimeConverter();
    }

    @Test
    public void convert_SourceFieldValueIsNull() {
        Object result = dateTimeConverter.convert(null, null, null, null);

        assertThat(result, is(nullValue()));
    }

    @Test(expected = MappingException.class)
    public void convert_ThrowsMappingException() {
        dateTimeConverter.convert(null, new String("test"), null, null);
    }

    @Test
    public void convert_SourceFromDate() {
        final int year = 1988;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        Date sourceFieldValue = cal.getTime();
        Object result = dateTimeConverter.convert(null, sourceFieldValue, DateTime.class,
                Date.class);

        assertThat(result, is(DateTime.class));
        assertThat(((DateTime) result).toDate(), is(cal.getTime()));
    }

    @Test
    public void convert_SourceFromDateTime() {
        DateTime sourceFieldValue = new DateTime();
        Object result = dateTimeConverter.convert(null, sourceFieldValue, DateTime.class,
                Date.class);

        assertThat(result, is(Date.class));
        assertThat(((Date) result), is(sourceFieldValue.toDate()));
    }
}
