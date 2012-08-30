package org.robbins.flashcards.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class EditTagPlace extends Place {

	private String placeName;

	public EditTagPlace(String token) {
		this.placeName = token;
	}

	public String getPlaceName() {
		return placeName;
	}

	@Prefix("editTag")
	public static class Tokenizer implements PlaceTokenizer<EditTagPlace> {

		@Override
		public String getToken(EditTagPlace place) {
			return place.getPlaceName();
		}

		@Override
		public EditTagPlace getPlace(String token) {
			return new EditTagPlace(token);
		}
	}
}
