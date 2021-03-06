
package org.robbins.flashcards.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ShellPlace extends Place {

    private final String shellName;

    public ShellPlace(final String token) {
        this.shellName = token;
    }

    public String getShellName() {
        return shellName;
    }

    public static class Tokenizer implements PlaceTokenizer<ShellPlace> {

        @Override
        public String getToken(final ShellPlace place) {
            return place.getShellName();
        }

        @Override
        public ShellPlace getPlace(final String token) {
            return new ShellPlace(token);
        }
    }
}
