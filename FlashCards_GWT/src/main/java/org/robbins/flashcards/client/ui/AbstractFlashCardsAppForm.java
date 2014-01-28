
package org.robbins.flashcards.client.ui;

import org.robbins.flashcards.client.factory.ClientFactory;

import com.google.gwt.user.client.ui.Composite;

public abstract class AbstractFlashCardsAppForm extends Composite implements
        IsValidatable {

    private AppConstants constants;

    public AbstractFlashCardsAppForm(ClientFactory clientFactory) {
        constants = clientFactory.getConstants();
    }

    public AppConstants getConstants() {
        return constants;
    }
}
