package org.robbins.flashcards.client.factory;

import org.robbins.flashcards.client.ui.AppConstants;
import org.robbins.flashcards.client.ui.EditFlashCardView;
import org.robbins.flashcards.client.ui.EditTagView;
import org.robbins.flashcards.client.ui.FlashCardsView;
import org.robbins.flashcards.client.ui.NavigationView;
import org.robbins.flashcards.client.ui.ShellView;
import org.robbins.flashcards.client.ui.TagsView;
import org.robbins.flashcards.model.UserDto;
import org.robbins.flashcards.service.FlashCardRestService;
import org.robbins.flashcards.service.TagRestService;
import org.robbins.flashcards.service.UserRestService;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;


public interface ClientFactory {

	// i18n contsants
	AppConstants getConstants();
	
	// the eventbus and placecontrollers
	EventBus getEventBus();
    PlaceController getPlaceController();

    // auth URLs
    String getLoginUrl();
    String getOpenIdUrl();
    
    // logged in User
    UserDto getLoggedInUser();
    void setLoggedInUser(UserDto loggedInUser);
    
    // the views
    ShellView getShellView();
    NavigationView getNavigationView();
    TagsView getTagsView();
    EditTagView getEditTagView();
    FlashCardsView getFlashCardsView();
    EditFlashCardView getEditFlashCardView();

    // services
    TagRestService getTagService();
    FlashCardRestService getFlashCardService();
    UserRestService getUserService();
}
