
package org.robbins.flashcards.webservices;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.flashcards.tests.webservices.BaseRestTest;
import org.robbins.flashcards.webservices.util.ResourceUrls;
import org.robbins.tests.IntegrationTest;
import org.springframework.test.context.ContextConfiguration;

@Category(IntegrationTest.class)
@ContextConfiguration(locations = { "classpath*:applicatonContext-webServices-test.xml" })
public class FlashCardsAppResourceIT extends BaseRestTest {

    private String getStatusUrl() {
        return getServerAddress() + ResourceUrls.status;
    }

    @Test
    public void status_FoundManifestAndVerson() {
        String result = getRestTemplate().getForObject(getStatusUrl(), String.class);

        assertThat(result, is(String.class));
        assertThat(result.length(), greaterThan(1));
    }
}
