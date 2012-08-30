package org.robbins.flashcards.client.ui.mobile;

import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.robbins.flashcards.client.ui.EditTagView;
import org.robbins.flashcards.client.ui.widgets.FlashCardFlexTable;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditTagViewImplMobile extends Composite implements EditTagView {

	private static TagFormUiBinder uiBinder = GWT.create(TagFormUiBinder.class);

	interface TagFormUiBinder extends UiBinder<Widget, EditTagViewImplMobile> {
	}

	public EditTagViewImplMobile() {
		GWT.log("Creating 'EditTagViewImplMobile'");
		
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	TextBox tagName;

	@UiField
	Label createdDate;

	@UiField
	Label modifiedDate;

	@UiField
	Button submit;

	@UiField
	Button cancel;

	@UiField
	FlashCardFlexTable flashCards;

	public HasText getName() {
		return tagName;
	}

	public HasText getCreatedDate() {
		return createdDate;
	}

	public HasText getModifiedDate() {
		return modifiedDate;
	}

	public HasClickHandlers getSaveButton() {
		return submit;
	}

	public HasClickHandlers getCancelButton() {
		return cancel;
	}

	public Widget asWidget() {
		return this;
	}

	@Override
	public void setTagData(Tag tag) {

		if (tag == null) {
			getName().setText("");
			getCreatedDate().setText("");
			getModifiedDate().setText("");

			return;
		}

		getName().setText(tag.getName());
		getCreatedDate().setText(
				"Created Date: "
						+ DateTimeFormat.forPattern("MM/dd/yyyy").print(
								tag.getCreatedDate()));
		getModifiedDate().setText(
				"Modified Date: "
						+ DateTimeFormat.forPattern("MM/dd/yyyy").print(
								tag.getLastModifiedDate()));
	}

	@Override
	public void setFlashCardsData(List<FlashCard> flashCards) {
		this.flashCards.setInput(flashCards);
		
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void initFormValidation() {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayTagNameValidationMessage(String message) {
		// TODO Auto-generated method stub
	}

	@Override
	public HasEnabled getSubmitEnabled() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public void enableForm(boolean enable) {
//		// TODO Auto-generated method stub
//	}
}
