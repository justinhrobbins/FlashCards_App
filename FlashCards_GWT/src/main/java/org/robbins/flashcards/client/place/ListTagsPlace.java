
package org.robbins.flashcards.client.place;

import com.google.gwt.place.shared.Place;

public class ListTagsPlace extends Place {

    private String placeName;

    public ListTagsPlace(String token) {
        this.placeName = token;
    }

    public String getPlaceName() {
        return placeName;
    }
}
