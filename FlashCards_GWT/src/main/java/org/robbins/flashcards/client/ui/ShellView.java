
package org.robbins.flashcards.client.ui;

import org.robbins.flashcards.model.UserDto;

import com.google.gwt.user.client.ui.IsWidget;

public interface ShellView extends IsWidget {

    void setLoggedInUser(UserDto user);

    void setLogoutToken(String token);
}
