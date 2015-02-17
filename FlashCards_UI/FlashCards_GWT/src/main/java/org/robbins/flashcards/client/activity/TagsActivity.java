
package org.robbins.flashcards.client.activity;

import java.util.List;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;
import org.robbins.flashcards.client.factory.ClientFactory;
import org.robbins.flashcards.client.place.EditFlashCardPlace;
import org.robbins.flashcards.client.place.EditTagPlace;
import org.robbins.flashcards.client.place.ListTagsPlace;
import org.robbins.flashcards.client.ui.TagsView;
import org.robbins.flashcards.events.DeleteTagEvent;
import org.robbins.flashcards.events.DeleteTagEventHandler;
import org.robbins.flashcards.events.LoadFlashCardEvent;
import org.robbins.flashcards.events.LoadFlashCardEventHandler;
import org.robbins.flashcards.events.LoadTagEvent;
import org.robbins.flashcards.events.LoadTagEventHandler;
import org.robbins.flashcards.model.TagDto;
import org.robbins.flashcards.service.TagRestService;
import org.robbins.flashcards.util.ConstsUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class TagsActivity extends AppAbstractActivity {

    private List<TagDto> tags;

    private final TagRestService tagService;

    private final EventBus eventBus;

    private final TagsView display;

    private final PlaceController placeController;

    public TagsActivity(final ClientFactory clientFactory) {
        super(clientFactory);

        GWT.log("Creating 'TagsActivity'");

        this.tagService = clientFactory.getTagService();
        this.eventBus = clientFactory.getEventBus();
        this.display = clientFactory.getTagsView();
        this.placeController = clientFactory.getPlaceController();

        ((RestServiceProxy) tagService).setResource(new Resource(""));

        getRegistrations().add(
                LoadTagEvent.register(this.eventBus, new LoadTagEventHandler() {

                    @Override
                    public void onLoadTag(final LoadTagEvent event) {
                        GWT.log("TagsActivity: 'Load Tag' event");
                        TagsActivity.this.placeController.goTo(new EditTagPlace(
                                event.getTagId()));
                    }
                }));

        getRegistrations().add(
                LoadFlashCardEvent.register(this.eventBus,
                        new LoadFlashCardEventHandler() {

                            @Override
                            public void onLoadFlashCard(final LoadFlashCardEvent event) {
                                GWT.log("FlashCardsActivity: 'Load FlashCard' event");
                                TagsActivity.this.placeController.goTo(new EditFlashCardPlace(
                                        event.getFlashCardId()));
                            }
                        }));

        getRegistrations().add(
                DeleteTagEvent.register(this.eventBus, new DeleteTagEventHandler() {

                    @Override
                    public void onDeleteTag(final DeleteTagEvent event) {
                        GWT.log("TagsActivity: 'Delete Tag' event");

                        tagService.deleteTags(ConstsUtil.DEFAULT_AUTH_HEADER,
                                event.getTagId(), new MethodCallback<java.lang.Void>() {

                                    @Override
                                    public void onFailure(final Method method,
                                            final Throwable caught) {
                                        GWT.log("DeleteTagActivity: Error deleting data");
                                        Window.alert(getConstants().errorDeletingTag());
                                    }

                                    @Override
                                    public void onSuccess(final Method method,
                                            final java.lang.Void result) {
                                        GWT.log("DeleteTagActivity: Tag Deleted:"
                                                + result);
                                        TagsActivity.this.placeController.goTo(new ListTagsPlace(
                                                ""));
                                    }
                                });
                    }
                }));
    }

    @Override
    public void start(final AcceptsOneWidget container, final EventBus eventBus) {
        container.setWidget(display.asWidget());

        String fields = ConstsUtil.DEFAULT_TAGS_LIST_FIELDS;
        fetchTagDetails(fields);
    }

    private void fetchTagDetails(final String fields) {

        // load the table with data
        tagService.getTags(ConstsUtil.DEFAULT_AUTH_HEADER, fields,
                new MethodCallback<List<TagDto>>() {

                    @Override
                    public void onFailure(final Method method, final Throwable caught) {
                        GWT.log("TagsActivity: Error loading data");
                        Window.alert(getConstants().errorLoadingTags());
                    }

                    @Override
                    public void onSuccess(final Method method, final List<TagDto> result) {
                        tags = result;
                        GWT.log("TagsActivity: Loading Tag list: " + tags.size()
                                + " tags");

                        display.setData(tags);
                    }
                });
    }
}
