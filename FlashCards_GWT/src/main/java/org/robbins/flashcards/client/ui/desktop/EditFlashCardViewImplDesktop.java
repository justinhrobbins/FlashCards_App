
package org.robbins.flashcards.client.ui.desktop;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.robbins.flashcards.client.factory.ClientFactory;
import org.robbins.flashcards.client.ui.AbstractFlashCardsAppForm;
import org.robbins.flashcards.client.ui.EditFlashCardView;
import org.robbins.flashcards.client.ui.widgets.LabelWidget;
import org.robbins.flashcards.client.ui.widgets.LinkPanelWidget;
import org.robbins.flashcards.client.ui.widgets.RichTextWidget;
import org.robbins.flashcards.client.ui.widgets.TextBoxWidget;
import org.robbins.flashcards.client.ui.widgets.autocomplete.InputListWidget;
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
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class EditFlashCardViewImplDesktop extends AbstractFlashCardsAppForm implements
        EditFlashCardView {

    private static FlashCardFormUiBinder uiBinder = GWT.create(FlashCardFormUiBinder.class);

    interface FlashCardFormUiBinder extends
            UiBinder<Widget, EditFlashCardViewImplDesktop> {
    }

    @UiField
    LabelWidget requiresLogin;

    @UiField
    TextBoxWidget question;

    @UiField
    RichTextWidget answer;

    @UiField(provided = true)
    LinkPanelWidget linksPanel;

    @UiField
    InputListWidget tagsWidget;

    @UiField
    Label createdDate;

    @UiField
    Label modifiedDate;

    @UiField
    Button submit;

    @UiField
    Button cancel;

    public EditFlashCardViewImplDesktop(ClientFactory clientFactory) {
        super(clientFactory);

        GWT.log("Creating 'EditFlashCardViewImplDesktop'");

        linksPanel = new LinkPanelWidget(clientFactory);

        initWidget(uiBinder.createAndBindUi(this));
        initFormValidation();
        requiresLogin.isRequired(false);
        question.setWidth("35em");
        question.setLabel(getConstants().question());
        question.addKeyDownHandler(new KeyDownHandler() {

            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    submit.click();
                }
            }
        });
        answer.setLabel(getConstants().answer());
        tagsWidget.setLabel(getConstants().tags());
        tagsWidget.isRequired(false);
    }

    @Override
    public void initFormValidation() {
        question.hideValidationMessage();
        answer.hideValidationMessage();
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
    public void setFlashCardData(FlashCardDto flashCard) {
        tagsWidget.removeItems();
        // getSubmitEnabled().setEnabled(true);

        if (flashCard == null) {
            getQuestion().setText(null);
            getAnswer().setHTML(null);
            getLinksPanel().addLinks(null);
            getCreatedDate().setText(null);
            getModifiedDate().setText(null);

            // had to add this in order for the setfocus() to work
            Scheduler.get().scheduleDeferred(new ScheduledCommand() {

                @Override
                public void execute() {
                    question.setFocus(true);
                }
            });
            return;
        }

        getQuestion().setText(flashCard.getQuestion());
        getAnswer().setHTML(flashCard.getAnswer());
        setTags(flashCard.getTags());
        getLinksPanel().addLinks(flashCard.getLinks());
        getCreatedDate().setText(
                getConstants().createdDate()
                        + DateTimeFormat.getFormat(
                                DateTimeFormat.PredefinedFormat.DATE_SHORT).format(
                                flashCard.getCreatedDate()));
        getModifiedDate().setText(
                getConstants().modifiedDate()
                        + DateTimeFormat.getFormat(
                                DateTimeFormat.PredefinedFormat.DATE_SHORT).format(
                                flashCard.getLastModifiedDate()));
        question.selectAll();
        question.setFocus(true);
    }

    private void setTags(Set<TagDto> tags) {
        List<String> tagList = new ArrayList<String>();
        if (tags.size() > 0) {
            for (TagDto tag : tags) {
                tagList.add(tag.getName());
            }
            tagsWidget.setSelected(tagList);
        }
    }

    @Override
    public void setTagsData(List<TagDto> tags) {
        if (tags == null) {
            return;
        }

        List<String> tagsList = new ArrayList<String>();
        for (TagDto tag : tags) {
            tagsList.add(tag.getName());
        }
        tagsWidget.setSuggestions(tagsList);
    }

    @Override
    public HasText getQuestion() {
        return question;
    }

    @Override
    public HasHTML getAnswer() {
        return answer;
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
    public List<String> getLinks() {
        return linksPanel.getLinks();
    }

    public LinkPanelWidget getLinksPanel() {
        return linksPanel;
    }

    @Override
    public boolean validate() {
        boolean result = true;

        if (question.getText().length() < 1) {
            question.dispayValidationMessage(getConstants().questionIsRequired());
            result = false;
        }
        if (answer.getText().length() < 1) {
            answer.dispayValidationMessage(getConstants().answerIsRequired());
            result = false;
        }
        return result;
    }

    @Override
    public void displayQuestionValidationMessage(String message) {
        question.dispayValidationMessage(message);
    }

    @Override
    public List<String> getTags() {
        return tagsWidget.getItemsSelected();
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
