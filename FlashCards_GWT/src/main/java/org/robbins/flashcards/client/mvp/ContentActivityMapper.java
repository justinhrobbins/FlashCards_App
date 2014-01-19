
package org.robbins.flashcards.client.mvp;

import org.robbins.flashcards.client.activity.AuthenticateActivity;
import org.robbins.flashcards.client.activity.EditFlashCardActivity;
import org.robbins.flashcards.client.activity.EditTagActivity;
import org.robbins.flashcards.client.activity.FlashCardsActivity;
import org.robbins.flashcards.client.activity.TagsActivity;
import org.robbins.flashcards.client.factory.ClientFactory;
import org.robbins.flashcards.client.place.EditFlashCardPlace;
import org.robbins.flashcards.client.place.EditTagPlace;
import org.robbins.flashcards.client.place.ListFlashCardsPlace;
import org.robbins.flashcards.client.place.ListTagsPlace;
import org.robbins.flashcards.client.place.LoginPlace;
import org.robbins.flashcards.client.place.LogoutPlace;
import org.robbins.flashcards.client.place.NewFlashCardPlace;
import org.robbins.flashcards.client.place.NewTagPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;

public class ContentActivityMapper implements ActivityMapper {

    private ClientFactory clientFactory;

    public ContentActivityMapper(ClientFactory clientFactory) {
        super();
        this.clientFactory = clientFactory;
    }

    @Override
    public Activity getActivity(Place place) {
        GWT.log("ContentActivityMapper - Place called: " + place);

        if (place instanceof ListTagsPlace) {
            return new TagsActivity(clientFactory);

        } else if (place instanceof EditTagPlace) {
            return new EditTagActivity((EditTagPlace) place, clientFactory);

        } else if (place instanceof NewTagPlace) {
            return new EditTagActivity((NewTagPlace) place, clientFactory);

        } else if (place instanceof ListFlashCardsPlace) {
            return new FlashCardsActivity(clientFactory);

        } else if (place instanceof EditFlashCardPlace) {
            return new EditFlashCardActivity((EditFlashCardPlace) place, clientFactory);

        } else if (place instanceof NewFlashCardPlace) {
            return new EditFlashCardActivity((NewFlashCardPlace) place, clientFactory);

        } else if (place instanceof LoginPlace) {
            return new AuthenticateActivity((LoginPlace) place, clientFactory);

        } else if (place instanceof LogoutPlace) {
            return new AuthenticateActivity((LogoutPlace) place, clientFactory);
        } else {
            // couldn't find an activity
            return null;
        }
    }
}
