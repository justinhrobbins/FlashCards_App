
package org.robbins.flashcards.client.ui;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasEnabled;

public interface HasSubmitButtons {

    HasClickHandlers getSaveButton();

    HasClickHandlers getCancelButton();

    HasEnabled getSubmitEnabled();
}
