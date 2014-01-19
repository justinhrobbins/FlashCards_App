
package org.robbins.flashcards.client.activity;

import java.util.List;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;
import org.robbins.flashcards.client.factory.ClientFactory;
import org.robbins.flashcards.client.place.EditFlashCardPlace;
import org.robbins.flashcards.client.place.EditTagPlace;
import org.robbins.flashcards.client.place.ListFlashCardsPlace;
import org.robbins.flashcards.client.ui.FlashCardsView;
import org.robbins.flashcards.events.DeleteFlashCardEvent;
import org.robbins.flashcards.events.DeleteFlashCardEventHandler;
import org.robbins.flashcards.events.LoadFlashCardEvent;
import org.robbins.flashcards.events.LoadFlashCardEventHandler;
import org.robbins.flashcards.events.LoadTagEvent;
import org.robbins.flashcards.events.LoadTagEventHandler;
import org.robbins.flashcards.model.FlashCardDto;
import org.robbins.flashcards.service.FlashCardRestService;
import org.robbins.flashcards.util.ConstsUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class FlashCardsActivity extends AppAbstractActivity {

    private List<FlashCardDto> flashCards;

    private final FlashCardRestService flashCardService;

    private final EventBus eventBus;

    private final FlashCardsView display;

    private PlaceController placeController;

    public FlashCardsActivity(ClientFactory clientFactory) {
        super(clientFactory);

        GWT.log("Creating 'FlashCardsActivity'");

        this.flashCardService = clientFactory.getFlashCardService();
        this.eventBus = clientFactory.getEventBus();
        this.display = clientFactory.getFlashCardsView();
        this.placeController = clientFactory.getPlaceController();

        ((RestServiceProxy) flashCardService).setResource(new Resource(""));

        getRegistrations().add(
                LoadFlashCardEvent.register(this.eventBus,
                        new LoadFlashCardEventHandler() {

                            @Override
                            public void onLoadFlashCard(LoadFlashCardEvent event) {
                                GWT.log("FlashCardsActivity: 'Load FlashCard' event");
                                FlashCardsActivity.this.placeController.goTo(new EditFlashCardPlace(
                                        Long.toString(event.getFlashCardId())));
                            }
                        }));

        getRegistrations().add(
                LoadTagEvent.register(this.eventBus, new LoadTagEventHandler() {

                    @Override
                    public void onLoadTag(LoadTagEvent event) {
                        GWT.log("TagsActivity: 'Load Tag' event");
                        FlashCardsActivity.this.placeController.goTo(new EditTagPlace(
                                Long.toString(event.getTagId())));
                    }
                }));

        getRegistrations().add(
                DeleteFlashCardEvent.register(this.eventBus,
                        new DeleteFlashCardEventHandler() {

                            @Override
                            public void onDeleteFlashcard(DeleteFlashCardEvent event) {
                                GWT.log("FlashCardsActivity: 'Delete FlashCard' event");

                                flashCardService.deleteFlashCards(
                                        ConstsUtil.DEFAULT_AUTH_HEADER,
                                        event.getFlashCardId(),
                                        new MethodCallback<java.lang.Void>() {

                                            @Override
                                            public void onFailure(Method method,
                                                    Throwable caught) {
                                                GWT.log("DeleteTagActivity: Error deleting data");
                                                Window.alert(getConstants().errorDeletingTag());
                                            }

                                            @Override
                                            public void onSuccess(Method method,
                                                    java.lang.Void result) {
                                                GWT.log("DeleteTagActivity: Tag Deleted");
                                                FlashCardsActivity.this.placeController.goTo(new ListFlashCardsPlace(
                                                        ""));
                                            }
                                        });
                            }
                        }));
    }

    @Override
    public void start(AcceptsOneWidget container, EventBus eventBus) {
        container.setWidget(display.asWidget());

        String fields = ConstsUtil.DEFAULT_FLASHCARDS_LIST_FIELDS;
        fetchFlashCardDetails(fields);
    }

    private void fetchFlashCardDetails(String fields) {

        // load the table with data
        flashCardService.getFlashCards(ConstsUtil.DEFAULT_AUTH_HEADER, fields,
                new MethodCallback<List<FlashCardDto>>() {

                    @Override
                    public void onFailure(Method method, Throwable caught) {
                        GWT.log("FlashCardsActivity: Error loading data");
                        Window.alert(getConstants().errorLoadingFlashCard());
                    }

                    @Override
                    public void onSuccess(Method method, List<FlashCardDto> result) {
                        flashCards = result;
                        GWT.log("FlashCardsActivity: Loading FlashCard list: "
                                + flashCards.size() + " flashCards");

                        // flashCardCellTable.setInput(flashCards);
                        display.setData(flashCards);
                    }
                });
    }
}
