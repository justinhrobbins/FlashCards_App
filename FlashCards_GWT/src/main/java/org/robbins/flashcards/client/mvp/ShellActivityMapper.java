package org.robbins.flashcards.client.mvp;

import org.robbins.flashcards.client.activity.AuthenticateActivity;
import org.robbins.flashcards.client.activity.ShellActivity;
import org.robbins.flashcards.client.factory.ClientFactory;
import org.robbins.flashcards.client.place.LoginPlace;
import org.robbins.flashcards.client.place.ShellPlace;
import org.robbins.flashcards.util.ConstsUtil;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;

public class ShellActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;
	
	public ShellActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public Activity getActivity(Place place) {
		GWT.log("ShellActivityMapper - Place called: " + place);
		
		if (place instanceof LoginPlace) {
			return new AuthenticateActivity(clientFactory);
/*
		} else if (clientFactory.getLoggedInUser() == null ){
			return new AuthenticateActivity(clientFactory);
*/
		} else {
			return new ShellActivity(new ShellPlace(ConstsUtil.SHELL_VIEW), clientFactory);
		}
	}
}
