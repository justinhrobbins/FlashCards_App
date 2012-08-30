package org.robbins.flashcards.client.ui.widgets;

import java.util.ArrayList;
import java.util.List;

import org.robbins.flashcards.model.Tag;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;

public class TagFlexTable extends FlexTable {

	public TagFlexTable() {
		super();
		this.setCellPadding(1);
		this.setCellSpacing(0);
		this.setWidth("100%");
	}

	public void setInput(List<Tag> tags) {
		for (int i = this.getRowCount(); i > 0; i--) {
			this.removeRow(0);
		}
		if (tags == null) {
			return;
		}

		setHeader();

		int i = 1;
		for (Tag tag : tags) {
			this.setWidget(i, 0, new Hyperlink(tag.getName(), "tagForm/" + tag.getId()));
			i++;
		}
	}

	private void setHeader() {
		List<String> headers = new ArrayList<String>();
		headers.add("Tags:");
		
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
