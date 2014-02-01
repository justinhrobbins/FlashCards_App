
package org.robbins.flashcards.webservices;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class FlashCardsResourceUT extends BaseMockingTest {

    private static final String TEST_MANIFEST_PATH = "test-manifests/TEST_MANIFEST.MF";

    private static final String TEST_BAD_MANIFEST_PATH = "test-manifests/TEST_BAD_MANIFEST.MF";

    private static final String FAKE_MANIFEST_PATH = "DOES_NOT_EXIST.MF";

    private static final String MANIFEST_PATH = "/META-INF/MANIFEST.MF";

    @Mock
    private ApplicationContext context;

    private FlashCardsAppResource flashCardsAppResource;

    @Before
    public void before() {
        flashCardsAppResource = new FlashCardsAppResource();
        ReflectionTestUtils.setField(flashCardsAppResource, "context", context);
    }

    @Test
    public void status_FoundManifestAndVerson() {
        Resource resource = new ClassPathResource(TEST_MANIFEST_PATH);
        when(context.getResource(MANIFEST_PATH)).thenReturn(resource);

        String result = flashCardsAppResource.getStatus();

        verify(context).getResource(MANIFEST_PATH);
        assertThat(result, is(String.class));
        assertThat(result, is("TEST_VERSION"));
    }

    @Test(expected = GenericWebServiceException.class)
    public void status_FoundManifest_VersionNotFound() {
        Resource resource = new ClassPathResource(TEST_BAD_MANIFEST_PATH);
        when(context.getResource(MANIFEST_PATH)).thenReturn(resource);

        flashCardsAppResource.getStatus();
    }

    @Test(expected = GenericWebServiceException.class)
    public void status_ManifestNotFound() {
        Resource resource = new ClassPathResource(FAKE_MANIFEST_PATH);
        when(context.getResource(MANIFEST_PATH)).thenReturn(resource);

        flashCardsAppResource.getStatus();
    }
}
