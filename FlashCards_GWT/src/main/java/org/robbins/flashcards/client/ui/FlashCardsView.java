
package org.robbins.flashcards.client.ui;

import java.util.List;

import org.robbins.flashcards.events.LoadFlashCardEvent;
import org.robbins.flashcards.model.FlashCardDto;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;

public interface FlashCardsView {

    HasClickHandlers getList();

    void setData(List<FlashCardDto> data);

    int getClickedRow(LoadFlashCardEvent event);

    Widget asWidget();
}