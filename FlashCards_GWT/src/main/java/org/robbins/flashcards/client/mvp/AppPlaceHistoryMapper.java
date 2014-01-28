
package org.robbins.flashcards.client.mvp;

import org.robbins.flashcards.client.place.AppTokenizer;
import org.robbins.flashcards.client.place.EditFlashCardPlace;
import org.robbins.flashcards.client.place.EditTagPlace;
import org.robbins.flashcards.client.place.NavigationPlace;
import org.robbins.flashcards.client.place.ShellPlace;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ AppTokenizer.class, EditTagPlace.Tokenizer.class,
    EditFlashCardPlace.Tokenizer.class, ShellPlace.Tokenizer.class,
    NavigationPlace.Tokenizer.class })
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
