
package org.robbins.flashcards.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class NavigationPlace extends Place {

    private final String placeName;

    public NavigationPlace(final String token) {
        this.placeName = token;
    }

    public String getPlaceName() {
        return placeName;
    }

    public static class Tokenizer implements PlaceTokenizer<NavigationPlace> {

        @Override
        public String getToken(final NavigationPlace place) {
            return place.getPlaceName();
        }

        @Override
        public NavigationPlace getPlace(final String token) {
            return new NavigationPlace(token);
        }
    }
}
