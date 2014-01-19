
package org.robbins.flashcards.client.place;

import com.google.gwt.place.shared.Place;

public class ListFlashCardsPlace extends Place {

    private String placeName;

    public ListFlashCardsPlace(String token) {
        this.placeName = token;
    }

    public String getPlaceName() {
        return placeName;
    }
}
