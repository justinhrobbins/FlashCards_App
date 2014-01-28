
package org.robbins.flashcards.client.place;

import org.robbins.flashcards.util.ConstsUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

@Prefix("")
public class AppTokenizer implements PlaceTokenizer<Place> {

    @Override
    public Place getPlace(String token) {
        GWT.log("AppTokenizer - Token: " + token);

        if (token.equals(ConstsUtil.TAG_FORM)) {
            return new NewTagPlace(token);

        } else if (token.equals(ConstsUtil.LIST_TAGS)) {
            return new ListTagsPlace(token);

        } else if (token.equals(ConstsUtil.FLASHCARD_FORM)) {
            return new NewFlashCardPlace(token);

        } else if (token.equals(ConstsUtil.LIST_FLASHCARDS)) {
            return new ListFlashCardsPlace(token);

        } else if (token.equals(ConstsUtil.LOGIN)) {
            return new LoginPlace(token);

        } else if (token.equals(ConstsUtil.LOGOUT)) {
            return new LogoutPlace(token);
        } else {
            // couldn't determine place
            GWT.log("Could not determine which Place to call");
            return null;
        }
    }

    @Override
    public String getToken(Place place) {

        if (place instanceof NewTagPlace) {
            return ((NewTagPlace) place).getPlaceName();

        } else if (place instanceof ListTagsPlace) {
            return ((ListTagsPlace) place).getPlaceName();

        } else if (place instanceof NewFlashCardPlace) {
            return ((NewFlashCardPlace) place).getPlaceName();

        } else if (place instanceof ListFlashCardsPlace) {
            return ((ListFlashCardsPlace) place).getPlaceName();

        } else if (place instanceof LoginPlace) {
            return ((LoginPlace) place).getPlaceName();

        } else if (place instanceof LogoutPlace) {
            return ((LogoutPlace) place).getPlaceName();

        } else {
            // couldn't find an activity
            return null;
        }
    }

}
