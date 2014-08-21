
package org.robbins.flashcards.client.ui.widgets.autocomplete;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

/**
 * This widget is to create
 * <p>
 * elements in a page.
 */
public class Paragraph extends Widget implements HasText {

    public Paragraph() {
        setElement(DOM.createElement("p"));
    }

    public Paragraph(final String text) {
        this();
        setText(text);
    }

    @Override
    public String getText() {
        return getElement().getInnerText();
    }

    @Override
    public void setText(final String text) {
        getElement().setInnerText(text);
    }
}
