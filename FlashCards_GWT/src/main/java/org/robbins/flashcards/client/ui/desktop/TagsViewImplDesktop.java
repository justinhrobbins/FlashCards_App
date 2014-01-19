
package org.robbins.flashcards.client.ui.desktop;

import java.util.Comparator;
import java.util.List;

import org.robbins.flashcards.client.factory.ClientFactory;
import org.robbins.flashcards.client.ui.AppConstants;
import org.robbins.flashcards.client.ui.TagsView;
import org.robbins.flashcards.client.ui.images.Images;
import org.robbins.flashcards.client.ui.widgets.DeleteTagImageCell;
import org.robbins.flashcards.client.ui.widgets.FlashCardListCell;
import org.robbins.flashcards.client.ui.widgets.ImageCell;
import org.robbins.flashcards.client.ui.widgets.LinkCell;
import org.robbins.flashcards.events.DeleteTagEvent;
import org.robbins.flashcards.events.LoadFlashCardEvent;
import org.robbins.flashcards.events.LoadTagEvent;
import org.robbins.flashcards.model.FlashCardDto;
import org.robbins.flashcards.model.TagDto;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.view.client.ListDataProvider;

public class TagsViewImplDesktop extends CellTable<TagDto> implements TagsView {

    private final EventBus eventBus;

    private final AppConstants constants;

    private LinkCell tagNameCell;

    private Column<TagDto, String> nameColumn;

    private FlashCardListCell flashCardsCell;

    private Column<TagDto, List<FlashCardDto>> flashCardsColumn;

    private ImageCell editCell;

    private Column<TagDto, ImageResource> editColumn;

    private DeleteTagImageCell deleteCell;

    private Column<TagDto, TagDto> deleteColumn;

    private ListDataProvider<TagDto> dataProvider;

    private List<TagDto> tagsList;

    private Images images = (Images) GWT.create(Images.class);

    public TagsViewImplDesktop(EventBus eventBus, int pageSize,
            CellTable.Resources resources, ClientFactory clientFactory) {
        super(pageSize, resources);

        GWT.log("Creating 'TagsViewImplDesktop'");
        this.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
        this.constants = clientFactory.getConstants();
        this.eventBus = eventBus;
        init();
    }

    public void init() {
        createColumns();
        formatCellTable();
    }

    private void bindData(List<TagDto> tags) {
        dataProvider = new ListDataProvider<TagDto>();

        // Connect the table to the data provider.
        dataProvider.addDataDisplay(this);

        tagsList = dataProvider.getList();
        for (TagDto tag : tags) {
            tagsList.add(tag);
        }
    }

    private void formatCellTable() {
        // Set the width of the table and put the table in fixed width mode.
        this.setWidth("100%", false);

        // Set the width of each column.
        this.setColumnWidth(nameColumn, 20.0, Unit.PCT);
        this.setColumnWidth(flashCardsColumn, 60.0, Unit.PCT);
        this.setColumnWidth(editColumn, 10.0, Unit.PCT);
        this.setColumnWidth(deleteColumn, 10.0, Unit.PCT);
    }

    private void createColumnSorting() {
        // Add a ColumnSortEvent.ListHandler to connect sorting to the List
        ListHandler<TagDto> nameColumnSortHandler = new ListHandler<TagDto>(tagsList);
        nameColumnSortHandler.setComparator(nameColumn, new Comparator<TagDto>() {

            @Override
            public int compare(TagDto o1, TagDto o2) {
                if (o1 == o2) {
                    return 0;
                }

                // Compare the name columns.
                if (o1 != null) {
                    return (o2 != null) ? o1.getName().compareTo(o2.getName()) : 1;
                }
                return -1;
            }

        });
        this.addColumnSortHandler(nameColumnSortHandler);

        // We know that the data is sorted alphabetically by default.
        this.getColumnSortList().push(nameColumn);
    }

    private void createColumns() {

        tagNameCell = new LinkCell();
        nameColumn = new Column<TagDto, String>(tagNameCell) {

            @Override
            public String getValue(TagDto object) {
                return object.getName();
            }
        };
        nameColumn.setSortable(true);
        this.addColumn(nameColumn, constants.tagName());

        // Add a field updater to be notified when the user enters a new name.
        nameColumn.setFieldUpdater(new FieldUpdater<TagDto, String>() {

            @Override
            public void update(int index, TagDto object, String value) {
                eventBus.fireEvent(new LoadTagEvent(object.getId()));
            }
        });

        flashCardsCell = new FlashCardListCell();
        flashCardsColumn = new Column<TagDto, List<FlashCardDto>>(flashCardsCell) {

            @Override
            public List<FlashCardDto> getValue(TagDto object) {
                return object.getFlashcardsAsList();
            }
        };
        flashCardsColumn.setSortable(false);
        this.addColumn(flashCardsColumn, constants.flashCards());

        // Add a field updater to be notified when the user enters a new name.
        flashCardsColumn.setFieldUpdater(new FieldUpdater<TagDto, List<FlashCardDto>>() {

            @Override
            public void update(int index, TagDto object, List<FlashCardDto> flashCardList) {
                GWT.log("update: " + flashCardList.get(0).getId());
                eventBus.fireEvent(new LoadFlashCardEvent(flashCardList.get(0).getId()));
            }
        });

        editCell = new ImageCell();
        editColumn = new Column<TagDto, ImageResource>(editCell) {

            @Override
            public void onBrowserEvent(Context context, Element elem, TagDto object,
                    NativeEvent event) {
                GWT.log("Click on Edit cell for: " + object.getId());
                eventBus.fireEvent(new LoadTagEvent(object.getId()));
            }

            @Override
            public ImageResource getValue(TagDto object) {
                return images.edit();
            }
        };
        editColumn.setSortable(false);
        this.addColumn(editColumn, constants.edit());

        deleteCell = new DeleteTagImageCell();
        deleteColumn = new Column<TagDto, TagDto>(deleteCell) {

            @Override
            public void onBrowserEvent(Context context, Element elem, TagDto object,
                    NativeEvent event) {
                if (object.getFlashcards().size() == 0) {
                    GWT.log("Click on Delete cell for: " + object.getId());
                    eventBus.fireEvent(new DeleteTagEvent(object.getId()));
                }
            }

            @Override
            public TagDto getValue(TagDto object) {
                return object;
            }
        };
        deleteColumn.setSortable(false);
        this.addColumn(deleteColumn, constants.delete());
    }

    @Override
    public HasClickHandlers getList() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setData(List<TagDto> data) {
        bindData(data);

        createColumnSorting();

        // Set the total row count. This isn't strictly necessary, but it affects
        // paging calculations, so its good habit to keep the row count up to date.
        this.setRowCount(tagsList.size(), true);
    }

    @Override
    public int getClickedRow(LoadTagEvent event) {
        // TODO Auto-generated method stub
        return 0;
    }
}
