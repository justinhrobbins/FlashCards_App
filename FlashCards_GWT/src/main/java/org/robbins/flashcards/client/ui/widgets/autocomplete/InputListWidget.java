
package org.robbins.flashcards.client.ui.widgets.autocomplete;

import java.util.ArrayList;
import java.util.List;

import org.robbins.flashcards.client.ui.AbstractWidget;
import org.robbins.flashcards.client.ui.widgets.LabelWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class InputListWidget extends AbstractWidget {

    interface Style extends CssResource {

        String auto_suggest();

        String original_token_input();

        String token_input_list_facebook();

        String token_input_token_facebook();

        String token_input_selected_token_facebook();

        String token_input_input_token_facebook();
    }

    private static InputListWidgetUiBinder uiBinder = GWT.create(InputListWidgetUiBinder.class);

    interface InputListWidgetUiBinder extends UiBinder<Widget, InputListWidget> {
    }

    private List<String> itemsSelected = new ArrayList<String>();

    private MultiWordSuggestOracle suggestionsOracle = new MultiWordSuggestOracle();

    private final BulletList bulletList = new BulletList();

    private final ListItem listItem = new ListItem();

    private final TextBox textBox = new TextBox();

    @UiField
    Style style;

    @UiField
    LabelWidget label;

    @UiField
    FlowPanel panel;

    public InputListWidget() {
        initWidget(uiBinder.createAndBindUi(this));

        // configure CSS styles
        this.bulletList.setStyleName(style.token_input_list_facebook());
        this.listItem.setStyleName(style.token_input_input_token_facebook());
        this.textBox.getElement().setAttribute("style",
                "outline-color: -moz-use-text-color; outline-style: none; outline-width: medium;");

        final SuggestBox suggestionBox = new SuggestBox(this.suggestionsOracle,
                this.textBox);
        suggestionBox.getElement().setId("suggestion_box");

        listItem.add(suggestionBox);
        bulletList.add(this.listItem);

        // this needs to be on the itemBox rather than box, or backspace will get executed
        // twice
        textBox.addKeyDownHandler(new KeyDownHandler() {

            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    // only allow manual entries with @ signs (assumed email addresses)
                    if (textBox.getValue().contains("@")) {
                        selectItem(textBox, bulletList);
                    }
                }
                if (event.getNativeKeyCode() == ' '
                        || event.getNativeKeyCode() == KeyCodes.KEY_TAB) {
                    if (textBox.getValue().length() > 0) {
                        selectItem(textBox, bulletList);
                    }
                }
                // handle backspace
                if (event.getNativeKeyCode() == KeyCodes.KEY_BACKSPACE) {
                    if ("".equals(textBox.getValue().trim())) {
                        ListItem li = (ListItem) bulletList.getWidget(bulletList.getWidgetCount() - 2);
                        Paragraph p = (Paragraph) li.getWidget(0);
                        if (itemsSelected.contains(p.getText())) {
                            itemsSelected.remove(p.getText());
                            GWT.log("Removing selected item '" + p.getText() + "'", null);
                            GWT.log("Remaining: " + itemsSelected, null);
                        }
                        bulletList.remove(li);
                        textBox.setFocus(true);
                    }
                }
            }
        });

        suggestionBox.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {

            @Override
            public void onSelection(
                    SelectionEvent<SuggestOracle.Suggestion> selectionEvent) {
                selectItem(textBox, bulletList);
            }
        });

        panel.add(bulletList);

        panel.getElement().setAttribute("onclick",
                "document.getElementById('suggestion_box').focus()");
        suggestionBox.setFocus(true);
    }

    public List<String> getItemsSelected() {
        return itemsSelected;
    }

    public void setSuggestions(MultiWordSuggestOracle suggestions) {
        this.suggestionsOracle = suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestionsOracle.clear();
        for (String suggestion : suggestions) {
            this.suggestionsOracle.add(suggestion);
        }
    }

    private void selectItem(final TextBox itemBox, final BulletList list) {
        if (itemBox.getValue() != null && !"".equals(itemBox.getValue().trim())) {
            /**
             * Change to the following structure: <li class="token_input_token_facebook">
             * <p>
             * What's New Scooby-Doo?
             * </p>
             * <span class="token_input_delete_token_facebook">x</span></li>
             */

            final ListItem displayItem = new ListItem();
            displayItem.setStyleName(style.token_input_token_facebook());
            Paragraph p = new Paragraph(itemBox.getValue());
            /*
             * displayItem.addClickHandler(new ClickHandler() { public void
             * onClick(ClickEvent clickEvent) {
             * displayItem.addStyleName(style.token_input_selected_token_facebook()); }
             * });
             */
            Span span = new Span("x");
            span.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent clickEvent) {
                    removeListItem(displayItem, list);
                }
            });

            displayItem.add(p);
            displayItem.add(span);
            // hold the original value of the item selected

            GWT.log("Adding selected item '" + itemBox.getValue() + "'", null);
            itemsSelected.add(itemBox.getValue());
            GWT.log("Total: " + itemsSelected, null);

            list.insert(displayItem, list.getWidgetCount() - 1);
            itemBox.setValue("");
            itemBox.setFocus(true);
        }
    }

    private void removeListItem(ListItem displayItem, BulletList list) {
        GWT.log("Removing: " + displayItem.getWidget(0).getElement().getInnerHTML(), null);
        itemsSelected.remove(displayItem.getWidget(0).getElement().getInnerHTML());
        list.remove(displayItem);
    }

    public void removeItems() {
        String tagToRemove;
        int num = bulletList.getWidgetCount();
        for (int i = num - 1; i >= 0; i--) {
            tagToRemove = ((ListItem) bulletList.getWidget(i)).getText();
            // if
            // (bulletList.getWidget(i).getElement().getInnerHTML().contains("<span>x</span>"))
            // {
            if (bulletList.getWidget(i).getElement().getInnerHTML().toLowerCase().contains(
                    ">x</span>")) {
                itemsSelected.remove(tagToRemove.substring(0, tagToRemove.length() - 1));
                bulletList.remove(bulletList.getWidget(i));
            }
        }
    }

    @Override
    public void setLabel(String text) {
        label.setText(text);
    }

    @Override
    public void isRequired(boolean required) {
        label.isRequired(required);
    }

    public void setSelected(List<String> list) {
        for (String name : list) {
            this.textBox.setValue(name);
            selectItem(this.textBox, bulletList);
        }
    }
}
