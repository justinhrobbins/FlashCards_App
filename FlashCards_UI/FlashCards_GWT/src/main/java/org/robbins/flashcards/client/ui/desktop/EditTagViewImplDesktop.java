
package org.robbins.flashcards.client.ui.desktop;

import java.util.List;

import org.robbins.flashcards.client.factory.ClientFactory;
import org.robbins.flashcards.client.ui.AbstractFlashCardsAppForm;
import org.robbins.flashcards.client.ui.EditTagView;
import org.robbins.flashcards.client.ui.widgets.FlashCardFlexTable;
import org.robbins.flashcards.client.ui.widgets.LabelWidget;
import org.robbins.flashcards.client.ui.widgets.TextBoxWidget;
import org.robbins.flashcards.model.FlashCardDto;
import org.robbins.flashcards.model.TagDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class EditTagViewImplDesktop extends AbstractFlashCardsAppForm implements
        EditTagView {

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

    @UiField(provided = true)
    FlashCardFlexTable flashCards;

    public EditTagViewImplDesktop(final ClientFactory clientFactory) {
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
            public void onKeyDown(final KeyDownEvent event) {
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

    @Override
    public HasText getCreatedDate() {
        return createdDate;
    }

    @Override
    public HasText getModifiedDate() {
        return modifiedDate;
    }

    @Override
    public HasClickHandlers getSaveButton() {
        return submit;
    }

    @Override
    public HasClickHandlers getCancelButton() {
        return cancel;
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public void setTagData(final TagDto tag) {
        // getSubmitEnabled().setEnabled(true);

        if (tag == null) {
            getName().setText("");
            getCreatedDate().setText("");
            getModifiedDate().setText("");

            // had to add this in order for the setfocus() to work
            Scheduler.get().scheduleDeferred(new ScheduledCommand() {

                @Override
                public void execute() {
                    tagName.setFocus(true);
                }
            });
            return;
        }

        getName().setText(tag.getName());

        getCreatedDate().setText(
                getConstants().createdDate()
                        + DateTimeFormat.getFormat(
                                DateTimeFormat.PredefinedFormat.DATE_SHORT).format(
                                tag.getCreatedDate()));

        getModifiedDate().setText(
                getConstants().modifiedDate()
                        + DateTimeFormat.getFormat(
                                DateTimeFormat.PredefinedFormat.DATE_SHORT).format(
                                tag.getLastModifiedDate()));

        tagName.selectAll();
        tagName.setFocus(true);
    }

    @Override
    public void setFlashCardsData(final List<FlashCardDto> flashCards) {
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
    public void displayTagNameValidationMessage(final String message) {
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

    // @Override
    // public void enableForm(boolean enable) {
    // if (enable == true) {
    // requiresLogin.hideValidationMessage();
    // }
    // else {
    // requiresLogin.dispayValidationMessage(getConstants().featureRequiresLogin());
    // }
    // getSubmitEnabled().setEnabled(enable);
    // }
}
