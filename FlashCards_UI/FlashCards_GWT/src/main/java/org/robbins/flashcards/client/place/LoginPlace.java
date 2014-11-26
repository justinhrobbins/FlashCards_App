
package org.robbins.flashcards.client.place;

import com.google.gwt.place.shared.Place;

public class LoginPlace extends Place {

    private final String placeName;

    public LoginPlace(final String token) {
        this.placeName = token;
    }

    public String getPlaceName() {
        return placeName;
    }
}
