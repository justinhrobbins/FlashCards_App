
package org.robbins.flashcards.client.ui.widgets.autocomplete;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class BulletList extends ComplexPanel {

    public BulletList() {
        setElement(DOM.createElement("UL"));
    }

    @Override
    public void add(final Widget w) {
        super.add(w, getElement());
    }

    public void insert(final Widget w, final int beforeIndex) {
        super.insert(w, getElement(), beforeIndex, true);
    }
}
