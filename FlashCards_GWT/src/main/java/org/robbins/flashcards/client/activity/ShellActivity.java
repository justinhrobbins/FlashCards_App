package org.robbins.flashcards.client.activity;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;
import org.robbins.flashcards.client.factory.ClientFactory;
import org.robbins.flashcards.client.mvp.AppPlaceHistoryMapper;
import org.robbins.flashcards.client.place.LoginPlace;
import org.robbins.flashcards.client.place.ShellPlace;
import org.robbins.flashcards.client.ui.ShellView;
import org.robbins.flashcards.model.UserDto;
import org.robbins.flashcards.service.UserRestService;
import org.robbins.flashcards.util.ConstsUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ShellActivity extends AppAbstractActivity {

	private final ShellView shellView;
	private ClientFactory clientFactory;
	private final UserRestService userService;
	private final AppPlaceHistoryMapper historyMapper;
	
	public ShellActivity(ShellPlace place, ClientFactory clientFactory) {
		super(clientFactory);
		
		GWT.log("Creating 'ShellActivity'");
		
		this.shellView = clientFactory.getShellView();
		this.clientFactory = clientFactory;
		this.userService = clientFactory.getUserService();
		
		((RestServiceProxy)userService).setResource(new Resource(""));
		
		historyMapper = GWT.create(AppPlaceHistoryMapper.class);
		setLoggedInUser();
	}
	
	@Override
	public void start(AcceptsOneWidget container, EventBus eventBus) {
		container.setWidget(shellView);
	}
	
	private void setLoggedInUser() {
		if (clientFactory.getLoggedInUser() == null) {
			ShellActivity.this.shellView.setLoggedInUser(null);
			this.shellView.setLogoutToken(historyMapper.getToken(new LoginPlace(ConstsUtil.LOGIN)));
			return;
		}
		
		this.shellView.setLogoutToken(historyMapper.getToken(new LoginPlace(ConstsUtil.LOGOUT)));
		if (clientFactory.getLoggedInUser().getEmail() != null 
				&& clientFactory.getLoggedInUser().getEmail().trim().length() > 0) {
			
			ShellActivity.this.shellView.setLoggedInUser(clientFactory.getLoggedInUser());
			return;
		}
		
		userService.getUser(ConstsUtil.DEFAULT_AUTH_HEADER, clientFactory.getLoggedInUser().getId(), new MethodCallback<UserDto>() {
			public void onFailure(Method method, Throwable caught) {
				GWT.log("ShellActivity: Error loading data");
				Window.alert(getConstants().errorLoadingTags());
			}
			public void onSuccess(Method method, UserDto user) {
				GWT.log("ShellActivity: Loading User: " + user.getEmail());
				
				// now that we have the full user info, update the user logged in user in the client factory
				clientFactory.setLoggedInUser(user);
				ShellActivity.this.shellView.setLoggedInUser(user);
			}
		});
	}
}
