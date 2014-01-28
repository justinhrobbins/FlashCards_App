
package org.robbins.flashcards.client.ui.widgets;

import org.robbins.flashcards.client.ui.IsRequired;
import org.robbins.flashcards.client.ui.desktop.style.FormStyleResource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class LabelWidget extends Composite implements HasText, IsRequired {

    private static LabelWidgetUiBinder uiBinder = GWT.create(LabelWidgetUiBinder.class);

    interface LabelWidgetUiBinder extends UiBinder<Widget, LabelWidget> {
    }

    @UiField
    InlineLabel displayName;

    @UiField
    SpanElement required;

    @UiField
    SpanElement validationContainer;

    @UiField
    InlineLabel validationText;

    public LabelWidget() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public LabelWidget(String text) {
        this();
    }

    public void dispayValidationMessage(String message) {
        validationText.setText(message);
        validationContainer.removeClassName(FormStyleResource.INSTANCE.formStyles().hidden());
    }

    public void hideValidationMessage() {
        validationText.setText("");
        validationContainer.addClassName(FormStyleResource.INSTANCE.formStyles().hidden());
    }

    @Override
    public String getText() {
        return displayName.getText();
    }

    @Override
    public void setText(String text) {
        displayName.setText(text);
    }

    @Override
    public void isRequired(boolean required) {
        if (required) {
            this.required.getStyle().setDisplay(Display.INLINE);
        } else {
            this.required.getStyle().setDisplay(Display.NONE);
        }
    }
}
