
package org.robbins.flashcards.webservices;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robbins.flashcards.client.FlashcardsAppClient;
import org.robbins.tests.IntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/applicatonContext-client.xml"})
public class FlashCardsAppResourceIT {

    @Inject
    private FlashcardsAppClient client;

    @Test
    public void status_FoundManifestAndVerson() {
        final String result = client.getStatus();

        assertThat(result, is(String.class));
        assertThat(result.length(), Matchers.greaterThan(1));
    }
}
