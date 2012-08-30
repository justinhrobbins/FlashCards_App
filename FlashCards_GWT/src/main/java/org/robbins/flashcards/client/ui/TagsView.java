package org.robbins.flashcards.client.ui;

import java.util.List;

import org.robbins.flashcards.events.LoadTagEvent;
import org.robbins.flashcards.model.Tag;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;

public interface TagsView {
	HasClickHandlers getList();
	void setData(List<Tag> data);
	int getClickedRow(LoadTagEvent event);
	Widget asWidget();
}