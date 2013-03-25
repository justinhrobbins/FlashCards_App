package org.robbins.flashcards.client.activity;

import java.util.ArrayList;
import java.util.List;

import org.robbins.flashcards.client.factory.ClientFactory;
import org.robbins.flashcards.client.ui.AppConstants;
import org.robbins.flashcards.client.ui.RequiresLogin;
import org.robbins.flashcards.model.UserDto;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.HandlerRegistration;

public abstract class AppAbstractActivity extends AbstractActivity {
	
	private final AppConstants constants;
	private final PlaceController placeController;
	private UserDto loggedInUser;
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	public AppAbstractActivity(ClientFactory clientFactory) {
		this.constants = clientFactory.getConstants();
		this.placeController = clientFactory.getPlaceController();
		this.loggedInUser = clientFactory.getLoggedInUser();
	}
	
	public List<HandlerRegistration> getRegistrations() {
		return registrations;
	}

	public AppConstants getConstants() {
		return constants;
	}

	public PlaceController getPlaceController() {
		return placeController;
	}
	
	public UserDto getLoggedInUser() {
		return loggedInUser;
	}
	
	public void requireLogin(RequiresLogin display) {
		if (getLoggedInUser() == null) {
			GWT.log("User is NOT logged in. Redicting to Login page");
			Cookies.setCookie("desitinationURL", Window.Location.getHref());
			display.enableForm(false);
		}
		else {
			display.enableForm(true);
		}
	}
	
	@Override
	public void onStop() {
		for (HandlerRegistration registration : registrations) {
			registration.removeHandler();
		}
		getRegistrations().clear();

		super.onStop();
	}
}
