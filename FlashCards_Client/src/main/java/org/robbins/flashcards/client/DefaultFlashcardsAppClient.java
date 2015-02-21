package org.robbins.flashcards.client;


import org.robbins.flashcards.client.util.ResourceUrls;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class DefaultFlashcardsAppClient extends AbstractClient implements FlashcardsAppClient {

    private String getStatusUrl() {
        return getServerAddress() + ResourceUrls.status;
    }

    @Override
    public String getStatus() {
        final HttpEntity httpEntity = new HttpEntity(getAuthHeaders());

        final ResponseEntity<String> response = getRestTemplate().exchange(getStatusUrl(), HttpMethod.GET,
                httpEntity, String.class);

        return response.getBody();
    }
}
