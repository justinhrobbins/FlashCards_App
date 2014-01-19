
package org.robbins.flashcards.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class NavigationPlace extends Place {

    private String placeName;

    public NavigationPlace(String token) {
        this.placeName = token;
    }

    public String getPlaceName() {
        return placeName;
    }

    public static class Tokenizer implements PlaceTokenizer<NavigationPlace> {

        @Override
        public String getToken(NavigationPlace place) {
            return place.getPlaceName();
        }

        @Override
        public NavigationPlace getPlace(String token) {
            return new NavigationPlace(token);
        }
    }
}
