
package org.robbins.flashcards.client.ui.widgets;

import org.robbins.flashcards.client.ui.AbstractWidget;
import org.robbins.flashcards.client.ui.desktop.style.FormStyleResource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.Widget;

public class RichTextWidget extends AbstractWidget implements HasText, HasHTML {

    private static RichTextWidgetUiBinder uiBinder = GWT.create(RichTextWidgetUiBinder.class);

    interface RichTextWidgetUiBinder extends UiBinder<Widget, RichTextWidget> {
    }

    @UiField
    LabelWidget label;

    @UiField
    FocusPanel panel;

    final RichTextArea richTextArea = new RichTextArea();

    RichTextToolbar toolbar = new RichTextToolbar(richTextArea);

    Grid grid;

    public RichTextWidget() {
        GWT.log("Creating 'RichTextWidget'");

        initRichTextArea();
        initWidget(uiBinder.createAndBindUi(this));
        panel.add(grid);
    }

    private void initRichTextArea() {
        richTextArea.ensureDebugId("cwRichText-area");
        richTextArea.setSize("100%", "14em");

        // Add the components to a panel
        grid = new Grid(2, 1);
        grid.setStyleName("cw-RichText");
        grid.setWidget(0, 0, toolbar);
        grid.setWidget(1, 0, richTextArea);
        grid.setBorderWidth(1);

        toolbar.ensureDebugId("cwRichText-toolbar");
        toolbar.setWidth("100%");

        richTextArea.addFocusHandler(new FocusHandler() {

            @Override
            public void onFocus(FocusEvent event) {
                hideValidationMessage();
            }
        });
    }

    @Override
    public String getHTML() {
        return richTextArea.getHTML();
    }

    @Override
    public void setHTML(String html) {
        richTextArea.setHTML(html);

    }

    @Override
    public String getText() {
        return richTextArea.getText();
    }

    @Override
    public void setText(String text) {
        richTextArea.setText(text);
    }

    @Override
    public void dispayValidationMessage(String message) {
        label.dispayValidationMessage(message);
        setBorderStyle(richTextArea,
                FormStyleResource.INSTANCE.formStyles().borderError());
    }

    @Override
    public void hideValidationMessage() {
        label.hideValidationMessage();
        setBorderStyle(richTextArea,
                FormStyleResource.INSTANCE.formStyles().borderHidden());
    }

    @Override
    public void setLabel(String text) {
        label.setText(text);
    }

    @Override
    public void isRequired(boolean required) {
        label.isRequired(required);
    }
}
