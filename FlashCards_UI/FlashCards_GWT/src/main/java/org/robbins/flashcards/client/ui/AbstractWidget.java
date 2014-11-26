
package org.robbins.flashcards.client.ui;

import org.robbins.flashcards.client.ui.desktop.style.FormStyleResource;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.UIObject;

public abstract class AbstractWidget extends Composite implements IsRequired,
        HasValidationMessage {

    public abstract void setLabel(String text);

    @Override
    public void dispayValidationMessage(final String message) {
        // TODO Auto-generated method stub
    }

    @Override
    public void hideValidationMessage() {
        // TODO Auto-generated method stub
    }

    protected void setBorderStyle(final UIObject uiObject, final String styleName) {
        uiObject.removeStyleName(FormStyleResource.INSTANCE.formStyles().borderOk());
        uiObject.removeStyleName(FormStyleResource.INSTANCE.formStyles().borderError());
        uiObject.removeStyleName(FormStyleResource.INSTANCE.formStyles().borderHidden());
        uiObject.removeStyleName(FormStyleResource.INSTANCE.formStyles().borderEmpty());
        uiObject.addStyleName(styleName);
    }
}
