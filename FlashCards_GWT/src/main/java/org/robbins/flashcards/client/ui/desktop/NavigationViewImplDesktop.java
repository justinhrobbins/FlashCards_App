
package org.robbins.flashcards.client.ui.desktop;

import org.robbins.flashcards.client.factory.ClientFactory;
import org.robbins.flashcards.client.ui.AppConstants;
import org.robbins.flashcards.client.ui.NavigationView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

public class NavigationViewImplDesktop extends Composite implements NavigationView {

    private static NavigationWidgetUiBinder uiBinder = GWT.create(NavigationWidgetUiBinder.class);

    interface NavigationWidgetUiBinder extends
            UiBinder<Widget, NavigationViewImplDesktop> {
    }

    @UiField
    Hyperlink newFlashCard;

    @UiField
    Hyperlink listFlashCards;

    @UiField
    Hyperlink newTag;

    @UiField
    Hyperlink listTags;

    public NavigationViewImplDesktop(ClientFactory clientFactory) {
        GWT.log("Creating 'NavigationViewImplDesktop'");

        initWidget(uiBinder.createAndBindUi(this));

        AppConstants constants = clientFactory.getConstants();

        newFlashCard.setText(constants.newFlashCard());
        listFlashCards.setText(constants.listFlashCards());
        newTag.setText(constants.newTag());
        listTags.setText(constants.listTags());
    }

    @Override
    public void setNewFlashCardToken(String token) {
        newFlashCard.setTargetHistoryToken(token);

    }

    @Override
    public void setListFlashCardsToken(String token) {
        listFlashCards.setTargetHistoryToken(token);
    }

    @Override
    public void setNewTagToken(String token) {
        newTag.setTargetHistoryToken(token);
    }

    @Override
    public void setListTagsToken(String token) {
        listTags.setTargetHistoryToken(token);
    }
}
