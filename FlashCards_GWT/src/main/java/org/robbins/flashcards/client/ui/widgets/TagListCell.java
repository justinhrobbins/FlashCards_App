package org.robbins.flashcards.client.ui.widgets;

import java.util.ArrayList;
import java.util.List;

import org.robbins.flashcards.model.TagDto;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class TagListCell extends AbstractCell<List<TagDto>>{

    /**
     * The HTML templates used to render the cell.
     */
    interface Templates extends SafeHtmlTemplates {
        /**
         * The template for this Cell, which includes styles and a value.
         * 
         * @param styles
         *            the styles to include in the style attribute of the div
         * @param value
         *            the safe value. Since the value type is {@link SafeHtml},
         *            it will not be escaped before including it in the
         *            template. Alternatively, you could make the value type
         *            String, in which case the value would be escaped.
         * @return a {@link SafeHtml} instance
         */
        @SafeHtmlTemplates.Template("<div name=\"{0}\" style=\"{1}\"><a href=\"javascript:;\">{2}</a></div>")
        SafeHtml cell(String name, SafeStyles styles, String value);
    }
    
    /**
     * Create a singleton instance of the templates used to render the cell.
     */
    private static Templates templates = GWT.create(Templates.class);
	
    public TagListCell() {
    	super("click", "keydown");
    }

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, List<TagDto> tagList, SafeHtmlBuilder sb) {
	    /*
	     * Always do a null check on the value. Cell widgets can pass null to
	     * cells if the underlying data contains a null, or if the data arrives
	     * out of order.
	     */

	    if (tagList == null) {
	        return;
	    }

	    // generate the cell
	    //SafeStyles tagStyle = SafeStylesUtils.fromTrustedString("float:left;");
	    SafeStyles tagStyle = SafeStylesUtils.fromTrustedString("background: none repeat scroll 0 0 #DEE7F8; border: 1px solid #CAD8F3; border-radius: 5px 5px 5px 5px; color: #555555; cursor: pointer; float: left; font-size: 11px; margin: 2px; padding: 0 5px;");
	    String toRender = "";

		for (TagDto tag : tagList) {
		/*
			if (toRender.length() > 0){
				toRender += "<div style=\"float:left\">,&nbsp;</div>";
			}
		*/
			toRender += templates.cell(Long.toString(tag.getId()), tagStyle, tag.getName()).asString();
		}
		sb.appendHtmlConstant(toRender);
	}
	
    /**
     * Called when an event occurs in a rendered instance of this Cell. The
     * parent element refers to the element that contains the rendered cell, NOT
     * to the outermost element that the Cell rendered.
     */
    @Override
    public void onBrowserEvent(Context context, Element parent, List<TagDto> tagList, NativeEvent event, ValueUpdater<List<TagDto>> valueUpdater) {
      // Let AbstractCell handle the keydown event.
      super.onBrowserEvent(context, parent, tagList, event, valueUpdater);

      // Handle the click event.
      if ("click".equals(event.getType())) {
        // Ignore clicks that occur outside of the outermost element.
        EventTarget eventTarget = event.getEventTarget();

        if (parent.isOrHasChild(Element.as(eventTarget))) {
            // use this to get the selected element!!
            Element el = Element.as(eventTarget);

            // check if we really click on the image
            if (el.getParentElement().getNodeName().equalsIgnoreCase("DIV")) {
                doAction(el.getParentElement().getAttribute("name"), valueUpdater);
            }
        }
      }
    }

    private void doAction(String value, ValueUpdater<List<TagDto>> valueUpdater) {
    	GWT.log("Cell clicked: " + value);
    	
    	// Trigger a value updater. In this case, the value doesn't actually
    	// change, but we use a ValueUpdater to let the app know that a value
    	// was clicked.
    	TagDto tag = new TagDto(Long.parseLong(value));
    	
    	ArrayList<TagDto> tagList = new ArrayList<TagDto>();
    	tagList.add(tag);
    	
    	if (valueUpdater != null) {
    		valueUpdater.update(tagList);
    	}
    }
}
