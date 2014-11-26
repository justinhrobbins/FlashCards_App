
package org.robbins.flashcards.client.place;

import com.google.gwt.place.shared.Place;

public class NewFlashCardPlace extends Place {

    private final String placeName;

    public NewFlashCardPlace(final String token) {
        this.placeName = token;
    }

    public String getPlaceName() {
        return placeName;
    }
}
