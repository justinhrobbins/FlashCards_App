
package org.robbins.flashcards.client.ui.widgets;

import org.robbins.flashcards.client.ui.AbstractWidget;
import org.robbins.flashcards.client.ui.desktop.style.FormStyleResource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class TextBoxWidget extends AbstractWidget implements HasText, HasKeyDownHandlers,
        Focusable {

    private static TextBoxWidgetUiBinder uiBinder = GWT.create(TextBoxWidgetUiBinder.class);

    interface TextBoxWidgetUiBinder extends UiBinder<Widget, TextBoxWidget> {
    }

    @UiField
    LabelWidget label;

    @UiField
    TextBox textBox;

    public TextBoxWidget() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("textBox")
    public void textBoxHasFocus(final FocusEvent event) {
        hideValidationMessage();
    }

    @Override
    public void dispayValidationMessage(final String message) {
        label.dispayValidationMessage(message);
        setBorderStyle(textBox, FormStyleResource.INSTANCE.formStyles().borderError());
    }

    @Override
    public void hideValidationMessage() {
        label.hideValidationMessage();
        setBorderStyle(textBox, FormStyleResource.INSTANCE.formStyles().borderEmpty());
    }

    @Override
    public String getText() {
        return textBox.getText();
    }

    @Override
    public void setText(final String text) {
        textBox.setText(text);
    }

    @Override
    public void setLabel(final String text) {
        label.setText(text);
    }

    @Override
    public void setWidth(final String width) {
        textBox.setWidth(width);
    }

    @Override
    public void isRequired(final boolean required) {
        label.isRequired(required);
    }

    @Override
    public int getTabIndex() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setAccessKey(final char key) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setFocus(final boolean focused) {
        textBox.setFocus(true);
    }

    @Override
    public void setTabIndex(final int index) {
        // TODO Auto-generated method stub
    }

    public void selectAll() {
        textBox.selectAll();
    }

    @Override
    public HandlerRegistration addKeyDownHandler(final KeyDownHandler handler) {
        return textBox.addKeyDownHandler(handler);
    }
}
