package org.robbins.flashcards.client.ui;

import com.google.gwt.user.client.ui.Widget;

public interface NavigationView {

    void setNewFlashCardToken(String token);
    void setListFlashCardsToken(String token);
    void setNewTagToken(String token);
    void setListTagsToken(String token);
    
	Widget asWidget();
}


