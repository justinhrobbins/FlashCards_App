
package org.robbins.flashcards.client.factory;

import org.robbins.flashcards.client.ui.AppConstants;
import org.robbins.flashcards.client.ui.EditFlashCardView;
import org.robbins.flashcards.client.ui.EditTagView;
import org.robbins.flashcards.client.ui.FlashCardsView;
import org.robbins.flashcards.client.ui.NavigationView;
import org.robbins.flashcards.client.ui.ShellView;
import org.robbins.flashcards.client.ui.TagsView;
import org.robbins.flashcards.client.ui.desktop.EditFlashCardViewImplDesktop;
import org.robbins.flashcards.client.ui.desktop.EditTagViewImplDesktop;
import org.robbins.flashcards.client.ui.desktop.FlashCardsViewImplDesktop;
import org.robbins.flashcards.client.ui.desktop.NavigationViewImplDesktop;
import org.robbins.flashcards.client.ui.desktop.ShellViewImplDesktop;
import org.robbins.flashcards.client.ui.desktop.TagsViewImplDesktop;
import org.robbins.flashcards.client.ui.desktop.style.CellTableResources;
import org.robbins.flashcards.client.ui.desktop.style.FormStyleResource;
import org.robbins.flashcards.model.UserDto;
import org.robbins.flashcards.service.FlashCardRestService;
import org.robbins.flashcards.service.TagRestService;
import org.robbins.flashcards.service.UserRestService;
import org.robbins.flashcards.util.ConstsUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Cookies;

public class ClientFactoryImpl implements ClientFactory {

    private final EventBus eventBus = new SimpleEventBus();
    private final PlaceController placeController = new PlaceController(eventBus);
    private UserDto loggedInUser;
    private final AppConstants appConstants = GWT.create(AppConstants.class);
    private final ShellView shellView = new ShellViewImplDesktop(this);
    private NavigationView navigationView;
    private TagsView tagsView;
    private EditTagView editTagView;
    private FlashCardsView flashCardsView;
    private EditFlashCardView editFlashCardView;
    private final TagRestService tagService = GWT.create(TagRestService.class);
    private final FlashCardRestService flashCardService = GWT.create(FlashCardRestService.class);
    private final UserRestService userService = GWT.create(UserRestService.class);
    private final int PAGE_SIZE = 20;

    @Override
    public String getLoginUrl() {
        return ConstsUtil.LOG_IN_URL;
    }

    @Override
    public String getOpenIdUrl() {
        return ConstsUtil.OPEN_ID_URL;
    }

    @Override
    public UserDto getLoggedInUser() {
        if (this.loggedInUser == null) {
            String userId = Cookies.getCookie(ConstsUtil.USER_ID);

            if (userId != null) {
                UserDto user = new UserDto(userId);
                setLoggedInUser(user);
            }
        }
        return this.loggedInUser;
    }

    @Override
    public void setLoggedInUser(final UserDto loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    @Override
    public ShellView getShellView() {
        return shellView;
    }

    @Override
    public AppConstants getConstants() {
        return appConstants;
    }

    @Override
    public NavigationView getNavigationView() {
        if (navigationView == null) {
            navigationView = createNavigationView();
        }
        return navigationView;
    }

    protected NavigationView createNavigationView() {
        return new NavigationViewImplDesktop(this);
    }

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public TagsView getTagsView() {

        if (tagsView == null) {
            tagsView = createTagsView();
        }
        return tagsView;
    }

    protected TagsView createTagsView() {
        CellTableResources.INSTANCE.cellTableStyle().ensureInjected();
        return new TagsViewImplDesktop(getEventBus(), PAGE_SIZE,
                CellTableResources.INSTANCE,
                this);
    }

    @Override
    public EditTagView getEditTagView() {
        if (editTagView == null) {
            editTagView = editTagsView();
        }
        return editTagView;
    }

    protected EditTagView editTagsView() {
        FormStyleResource.INSTANCE.formStyles().ensureInjected();
        return new EditTagViewImplDesktop(this);
    }

    @Override
    public FlashCardsView getFlashCardsView() {

        if (flashCardsView == null) {
            flashCardsView = createFlashCardsView();
        }
        return flashCardsView;
    }

    protected FlashCardsView createFlashCardsView() {
        CellTableResources.INSTANCE.cellTableStyle().ensureInjected();
        return new FlashCardsViewImplDesktop(getEventBus(), PAGE_SIZE,
                CellTableResources.INSTANCE, this);
    }

    @Override
    public EditFlashCardView getEditFlashCardView() {
        if (editFlashCardView == null) {
            editFlashCardView = editFlashCardsView();
        }
        return editFlashCardView;
    }

    protected EditFlashCardView editFlashCardsView() {
        FormStyleResource.INSTANCE.formStyles().ensureInjected();
        return new EditFlashCardViewImplDesktop(this);
    }

    @Override
    public PlaceController getPlaceController() {
        return placeController;
    }

    @Override
    public TagRestService getTagService() {
        return tagService;
    }

    @Override
    public FlashCardRestService getFlashCardService() {
        return flashCardService;
    }

    @Override
    public UserRestService getUserService() {
        return userService;
    }
}
