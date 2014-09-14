
package org.robbins.flashcards.webservices;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robbins.flashcards.client.FlashcardsAppClient;
import org.robbins.tests.IntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicatonContext-client.xml"})
public class FlashCardsAppResourceIT {

    @Inject
    private FlashcardsAppClient client;

    @Test
    public void status_FoundManifestAndVerson() {
        String result = client.getStatus();

        assertThat(result, is(String.class));
        assertThat(result.length(), greaterThan(1));
    }
}
