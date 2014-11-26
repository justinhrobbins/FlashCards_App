
package org.robbins.flashcards.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class EditFlashCardPlace extends Place {

    private final String placeName;

    public EditFlashCardPlace(final String token) {
        this.placeName = token;
    }

    public String getPlaceName() {
        return placeName;
    }

    @Prefix("editFlashCard")
    public static class Tokenizer implements PlaceTokenizer<EditFlashCardPlace> {

        @Override
        public String getToken(final EditFlashCardPlace place) {
            return place.getPlaceName();
        }

        @Override
        public EditFlashCardPlace getPlace(final String token) {
            return new EditFlashCardPlace(token);
        }

    }
}
