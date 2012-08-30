package org.robbins.flashcards.client.place;

import com.google.gwt.place.shared.Place;

public class NewTagPlace extends Place {

	private String placeName;

	public NewTagPlace(String token) {
		this.placeName = token;
	}

	public String getPlaceName() {
		return placeName;
	}
}
