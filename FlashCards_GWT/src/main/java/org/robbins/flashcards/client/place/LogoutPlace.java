
package org.robbins.flashcards.client.place;

import com.google.gwt.place.shared.Place;

public class LogoutPlace extends Place {

    private String placeName;

    public LogoutPlace(String token) {
        this.placeName = token;
    }

    public String getPlaceName() {
        return placeName;
    }
}
