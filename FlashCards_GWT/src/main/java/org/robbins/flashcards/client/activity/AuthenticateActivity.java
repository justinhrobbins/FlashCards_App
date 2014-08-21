
package org.robbins.flashcards.client.activity;

import org.robbins.flashcards.client.factory.ClientFactory;
import org.robbins.flashcards.client.place.LoginPlace;
import org.robbins.flashcards.client.place.LogoutPlace;
import org.robbins.flashcards.client.place.ShellPlace;
import org.robbins.flashcards.util.ConstsUtil;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AuthenticateActivity extends AbstractActivity {

    private final ClientFactory clientFactory;

    private final PlaceController placeController;

    public AuthenticateActivity(final ClientFactory clientFactory) {
        GWT.log("Creating 'AuthenticateActivity'");

        this.clientFactory = clientFactory;
        this.placeController = clientFactory.getPlaceController();
    }

    public AuthenticateActivity(final LoginPlace place, final ClientFactory clientFactory) {
        this(clientFactory);
        GWT.log("Creating 'AuthenticateActivity' for 'LoginPlace'");
        redirect(clientFactory.getLoginUrl());
    }

    public AuthenticateActivity(final LogoutPlace place, final ClientFactory clientFactory) {
        this(clientFactory);
        GWT.log("Creating 'AuthenticateActivity' for 'LogoutPlace'");
        GWT.log("Logging out user: '" + clientFactory.getLoggedInUser().getEmail() + "'");
        Cookies.removeCookie(ConstsUtil.USER_ID);
        clientFactory.setLoggedInUser(null);

        AuthenticateActivity.this.placeController.goTo(new ShellPlace(
                ConstsUtil.SHELL_VIEW));
    }

    @Override
    public void start(final AcceptsOneWidget container, final EventBus eventBus) {

    }

    // redirect the browser to the given url
    public static native void redirect(final String url)/*-{
		$wnd.location = url;
    }-*/;
}
