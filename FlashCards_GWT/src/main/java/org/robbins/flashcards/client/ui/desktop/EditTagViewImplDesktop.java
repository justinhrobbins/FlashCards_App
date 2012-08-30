package org.robbins.flashcards.client.ui.desktop;

import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.robbins.flashcards.client.factory.ClientFactory;
import org.robbins.flashcards.client.ui.AbstractFlashCardsAppForm;
import org.robbins.flashcards.client.ui.EditTagView;
import org.robbins.flashcards.client.ui.widgets.FlashCardFlexTable;
import org.robbins.flashcards.client.ui.widgets.LabelWidget;
import org.robbins.flashcards.client.ui.widgets.TextBoxWidget;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class EditTagViewImplDesktop extends AbstractFlashCardsAppForm implements EditTagView {

	private static TagFormUiBinder uiBinder = GWT.create(TagFormUiBinder.class);

	interface TagFormUiBinder extends UiBinder<Widget, EditTagViewImplDesktop> {
	}

	@UiField
	LabelWidget requiresLogin;
	
	@UiField
	TextBoxWidget tagName;

	@UiField
	Label createdDate;

	@UiField
	Label modifiedDate;

	@UiField
	Button submit;

	@UiField
	Button cancel;

	@UiField(provided=true)
	FlashCardFlexTable flashCards;
	
	public EditTagViewImplDesktop(ClientFactory clientFactory) {
		super(clientFactory);
		
		GWT.log("Creating 'EditTagViewImplDesktop'");
		flashCards = new FlashCardFlexTable(clientFactory);

		initWidget(uiBinder.createAndBindUi(this));
		initFormValidation();
		requiresLogin.isRequired(false);
		tagName.setLabel("Name:");
		tagName.setWidth("35em");
		tagName.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					submit.click();
				}
			}
		});
	}

	@Override
	public void initFormValidation() {
		tagName.hideValidationMessage();
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
		//getSubmitEnabled().setEnabled(true);
		
		if (tag == null) {
			getName().setText("");
			getCreatedDate().setText("");
			getModifiedDate().setText("");
			
			// had to add this in order for the setfocus() to work
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
		        public void execute () {
		        	tagName.setFocus(true);
		        }
			});
			return;
		}

		getName().setText(tag.getName());
		
		getCreatedDate().setText(
				getConstants().createdDate()
						+ DateTimeFormat.forPattern("MM/dd/yyyy").print(
								tag.getCreatedDate()));
		
		getModifiedDate().setText(
				getConstants().modifiedDate()
						+ DateTimeFormat.forPattern("MM/dd/yyyy").print(
								tag.getLastModifiedDate()));
		
		tagName.selectAll();
		tagName.setFocus(true);
	}

	@Override
	public void setFlashCardsData(List<FlashCard> flashCards) {
		this.flashCards.setInput(flashCards);
		
	}

	@Override
	public boolean validate() {
		boolean result = true;
		
		if (tagName.getText().length() < 1) {
			displayTagNameValidationMessage(getConstants().tagNameIsRequired());
			result = false;
		}
		return result;
	}
	
	@Override
	public void displayTagNameValidationMessage(String message) {
		tagName.dispayValidationMessage(message);
	}

	@Override
	public HasText getName() {
		return this.tagName;
	}
	
	@Override
	public HasEnabled getSubmitEnabled() {
		return submit;
	}

//	@Override
//	public void enableForm(boolean enable) {
//		if (enable == true) {
//			requiresLogin.hideValidationMessage();			
//		}
//		else {
//			requiresLogin.dispayValidationMessage(getConstants().featureRequiresLogin());
//		}
//		getSubmitEnabled().setEnabled(enable);		
//	}
}
