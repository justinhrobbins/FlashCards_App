package org.robbins.flashcards.client;


import org.robbins.flashcards.client.util.ResourceUrls;
import org.springframework.stereotype.Component;

@Component
public class DefaultFlashcardsAppClient extends AbstractClient implements FlashcardsAppClient {

    private String getStatusUrl() {
        return getServerAddress() + ResourceUrls.status;
    }

    @Override
    public String getStatus() {
        return getRestTemplate().getForObject(getStatusUrl(), String.class);
    }
}
