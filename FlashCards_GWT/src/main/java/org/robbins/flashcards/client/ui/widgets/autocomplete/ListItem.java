
package org.robbins.flashcards.client.ui.widgets.autocomplete;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class ListItem extends ComplexPanel implements HasText, HasHTML, HasClickHandlers,
        HasKeyDownHandlers, HasBlurHandlers {

    HandlerRegistration clickHandler;

    public ListItem() {
        setElement(DOM.createElement("LI"));
    }

    @Override
    public void add(Widget w) {
        super.add(w, getElement());
    }

    public void insert(Widget w, int beforeIndex) {
        super.insert(w, getElement(), beforeIndex, true);
    }

    @Override
    public String getText() {
        return DOM.getInnerText(getElement());
    }

    @Override
    public void setText(String text) {
        DOM.setInnerText(getElement(), (text == null) ? "" : text);
    }

    public void setId(String id) {
        DOM.setElementAttribute(getElement(), "id", id);
    }

    @Override
    public String getHTML() {
        return DOM.getInnerHTML(getElement());
    }

    @Override
    public void setHTML(String html) {
        DOM.setInnerHTML(getElement(), (html == null) ? "" : html);
    }

    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return addDomHandler(handler, ClickEvent.getType());
    }

    @Override
    public HandlerRegistration addKeyDownHandler(KeyDownHandler handler) {
        return addDomHandler(handler, KeyDownEvent.getType());
    }

    @Override
    public HandlerRegistration addBlurHandler(BlurHandler handler) {
        return addDomHandler(handler, BlurEvent.getType());
    }
}