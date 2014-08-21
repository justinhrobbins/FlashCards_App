
package org.robbins.flashcards.client.place;

import com.google.gwt.place.shared.Place;

public class ListTagsPlace extends Place {

    private final String placeName;

    public ListTagsPlace(final String token) {
        this.placeName = token;
    }

    public String getPlaceName() {
        return placeName;
    }
}
