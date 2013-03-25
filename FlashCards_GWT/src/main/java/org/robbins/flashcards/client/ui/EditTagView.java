package org.robbins.flashcards.client.ui;

import java.util.List;

import org.robbins.flashcards.model.FlashCardDto;
import org.robbins.flashcards.model.TagDto;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public interface EditTagView extends IsValidatable, HasSubmitButtons {
	
	HasText getName();
	void displayTagNameValidationMessage(String message);
	
	HasText getCreatedDate();
	HasText getModifiedDate();
	
	HasClickHandlers getSaveButton();
	HasClickHandlers getCancelButton();

	void setTagData(TagDto data);
	void setFlashCardsData(List<FlashCardDto> flashCards);
	
	Widget asWidget();
}
