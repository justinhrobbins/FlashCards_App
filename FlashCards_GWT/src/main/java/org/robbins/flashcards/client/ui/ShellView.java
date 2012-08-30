package org.robbins.flashcards.client.ui;

import org.robbins.flashcards.model.User;

import com.google.gwt.user.client.ui.IsWidget;

public interface ShellView extends IsWidget {
	void setLoggedInUser(User user);
	void setLogoutToken(String token);
}
