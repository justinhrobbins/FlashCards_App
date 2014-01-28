
package org.robbins.flashcards.client.ui.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LinkWidget extends Composite {

    private static LinkWidgetUiBinder uiBinder = GWT.create(LinkWidgetUiBinder.class);

    interface LinkWidgetUiBinder extends UiBinder<Widget, LinkWidget> {
    }

    @UiField
    Label linkLabel;

    @UiField
    SpanElement linkContainer;

    @UiField
    Anchor linkAnchor;

    @UiField
    TextBox linkTextBox;

    @UiField
    Image editImage;

    @UiField
    Image deleteImage;

    private String link;

    public LinkWidget() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("linkTextBox")
    void onKeyUp(KeyUpEvent event) {
        this.link = this.linkTextBox.getText();
        this.linkAnchor.setText(this.linkTextBox.getText());
    }

    @UiHandler("editImage")
    void onEditClick(ClickEvent event) {
        linkContainer.getStyle().setDisplay(Display.NONE);
        linkTextBox.setVisible(true);
    }

    @UiHandler("deleteImage")
    void onDeleteClick(ClickEvent event) {
        LinkWidget.this.removeFromParent();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;

        this.linkContainer.getStyle().setDisplay(Display.INLINE_BLOCK);
        this.linkAnchor.setText(link);
        this.linkAnchor.setHref(link);
        this.linkAnchor.setTarget("_blank");
        this.linkAnchor.setVisible(true);

        this.linkTextBox.setText(link);
        this.linkTextBox.setVisible(false);
    }

    public HasText getLinkLabel() {
        return linkLabel;
    }
}
