
package org.robbins.flashcards.client.ui.widgets;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class ImageCell extends AbstractCell<ImageResource> {

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
        SafeHtml cell(SafeHtml value);
    }

    /**
     * Create a singleton instance of the templates used to render the cell.
     */
    private static Templates templates = GWT.create(Templates.class);

    public ImageCell() {
        super("click");
    }

    @Override
    public void render(Context context, ImageResource value, SafeHtmlBuilder sb) {
        if (value != null) {

            SafeHtml html = SafeHtmlUtils.fromTrustedString(AbstractImagePrototype.create(
                    value).getHTML());
            sb.append(templates.cell(html));
        }
    }
}
