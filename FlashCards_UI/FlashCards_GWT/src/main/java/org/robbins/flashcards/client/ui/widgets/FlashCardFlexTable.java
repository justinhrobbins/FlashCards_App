
package org.robbins.flashcards.client.ui.widgets;

import java.util.ArrayList;
import java.util.List;

import org.robbins.flashcards.client.factory.ClientFactory;
import org.robbins.flashcards.client.ui.AppConstants;
import org.robbins.flashcards.model.FlashCardDto;
import org.robbins.flashcards.util.ConstsUtil;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;

public class FlashCardFlexTable extends FlexTable {

    private final AppConstants constants;

    public FlashCardFlexTable(final ClientFactory clientFactory) {
        super();

        this.constants = clientFactory.getConstants();
        this.setCellPadding(1);
        this.setCellSpacing(0);
        this.setWidth("100%");
    }

    public void setInput(final List<FlashCardDto> flashCards) {
        for (int i = this.getRowCount(); i > 0; i--) {
            this.removeRow(0);
        }

        setHeader();

        if (flashCards == null || flashCards.size() == 0) {
            this.setWidget(1, 0,
                    new Label(constants.thisTagIsNotAssignedToAnyFlashCards()));
            return;
        }

        int i = 1;
        for (FlashCardDto flashCard : flashCards) {
            this.setWidget(i, 0, new Hyperlink(flashCard.getQuestion(),
                    ConstsUtil.EDIT_FLASHCARD + ":" + flashCard.getId()));
            i++;
        }
    }

    private void setHeader() {
        List<String> headers = new ArrayList<String>();
        headers.add(constants.flashCards());

        int row = 0;
        if (headers != null) {
            int i = 0;
            for (String string : headers) {
                this.setText(row, i, string);
                i++;
            }
            row++;
        }
        // Make the table header look nicer
        this.getRowFormatter().addStyleName(0, "tableHeader");
    }
}
