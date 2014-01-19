
package org.robbins.flashcards.client.ui.desktop.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;

public interface CellTableResources extends CellTable.Resources {

    /**
     * The styles applied to the table.
     */
    interface TableStyle extends CellTable.Style {
    }

    public CellTableResources INSTANCE = GWT.create(CellTableResources.class);

    @Override
    @Source({ CellTable.Style.DEFAULT_CSS, "CellTableStyle.css" })
    TableStyle cellTableStyle();

}