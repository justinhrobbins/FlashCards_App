
package org.robbins.flashcards.client.ui.widgets;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

public class LinkCell extends AbstractCell<String> {

    /**
     * The HTML templates used to render the cell.
     */
    interface Templates extends SafeHtmlTemplates {

        /**
         * The template for this Cell, which includes styles and a value.
         *
         * @param styles the styles to include in the style attribute of the div
         * @param value the safe value. Since the value type is {@link SafeHtml}, it will
         *        not be escaped before including it in the template. Alternatively, you
         *        could make the value type String, in which case the value would be
         *        escaped.
         * @return a {@link SafeHtml} instance
         */
        @SafeHtmlTemplates.Template("<a href=\"javascript:;\">{0}</a>")
        SafeHtml anchor(SafeHtml value);
    }

    /**
     * Create a singleton instance of the templates used to render the cell.
     */
    private static Templates templates = GWT.create(Templates.class);

    public LinkCell() {
        super("click", "keydown");
    }

    @Override
    public void render(final com.google.gwt.cell.client.Cell.Context context,
            final String value, final SafeHtmlBuilder sb) {
        /*
         * Always do a null check on the value. Cell widgets can pass null to cells if the
         * underlying data contains a null, or if the data arrives out of order.
         */
        if (value == null) {
            return;
        }

        // If the value comes from the user, we escape it to avoid XSS attacks.
        SafeHtml safeValue = SafeHtmlUtils.fromString(value);

        // Use the template to create the Cell's html.
        SafeHtml rendered = templates.anchor(safeValue);
        sb.append(rendered);
    }

    /**
     * Called when an event occurs in a rendered instance of this Cell. The parent element
     * refers to the element that contains the rendered cell, NOT to the outermost element
     * that the Cell rendered.
     */
    @Override
    public void onBrowserEvent(final Context context, final Element parent,
            final String value, final NativeEvent event,
            final ValueUpdater<String> valueUpdater) {
        // Let AbstractCell handle the keydown event.
        super.onBrowserEvent(context, parent, value, event, valueUpdater);

        // Handle the click event.
        if ("click".equals(event.getType())) {
            // Ignore clicks that occur outside of the outermost element.
            EventTarget eventTarget = event.getEventTarget();
            if (parent.getFirstChildElement().isOrHasChild(Element.as(eventTarget))) {
                GWT.log("LinkCell: Cell clicked");
                doAction(value, valueUpdater);
            }
        }
    }

    /**
     * onEnterKeyDown is called when the user presses the ENTER key will the Cell is
     * selected. You are not required to override this method, but its a common convention
     * that allows your cell to respond to key events.
     */
    @Override
    protected void onEnterKeyDown(final Context context, final Element parent,
            final String value, final NativeEvent event,
            final ValueUpdater<String> valueUpdater) {
        doAction(value, valueUpdater);
    }

    private void doAction(final String value, final ValueUpdater<String> valueUpdater) {
        // Trigger a value updater. In this case, the value doesn't actually
        // change, but we use a ValueUpdater to let the app know that a value
        // was clicked.
        if (valueUpdater != null) {
            valueUpdater.update(value);
        }
    }
}
