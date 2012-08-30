package org.robbins.flashcards.client.place;

import com.google.gwt.place.shared.Place;

public class LoginPlace extends Place {

	private String placeName;

	public LoginPlace(String token) {
		this.placeName = token;
	}

	public String getPlaceName() {
		return placeName;
	}
}
