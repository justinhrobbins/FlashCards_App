
package org.robbins.flashcards.client.place;

import com.google.gwt.place.shared.Place;

public class NewTagPlace extends Place {

    private final String placeName;

    public NewTagPlace(final String token) {
        this.placeName = token;
    }

    public String getPlaceName() {
        return placeName;
    }
}
