
package org.robbins.flashcards.client.ui.mobile;

import java.util.Comparator;
import java.util.List;

import org.robbins.flashcards.client.ui.TagsView;
import org.robbins.flashcards.client.ui.widgets.LinkCell;
import org.robbins.flashcards.events.LoadTagEvent;
import org.robbins.flashcards.model.TagDto;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

public class TagsViewImplMobile extends CellTable<TagDto> implements TagsView {

    private final EventBus eventBus;

    private LinkCell nameCell;

    private Column<TagDto, String> nameColumn;

    private ListDataProvider<TagDto> dataProvider;

    private List<TagDto> tagsList;

    public TagsViewImplMobile(EventBus eventBus) {
        super();

        GWT.log("Creating 'TagsViewImplMobile'");
        this.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
        this.eventBus = eventBus;
        init();
    }

    public TagsViewImplMobile(EventBus eventBus, ProvidesKey<TagDto> keyProvider) {
        super(keyProvider);

        GWT.log("Creating 'TagsViewImplMobile'");
        this.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
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
        this.setWidth("100%", true);

        // Set the width of each column.
        this.setColumnWidth(nameColumn, 100.0, Unit.PCT);
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

        nameCell = new LinkCell();
        nameColumn = new Column<TagDto, String>(nameCell) {

            @Override
            public String getValue(TagDto object) {
                return object.getName();
            }
        };
        nameColumn.setSortable(true);
        this.addColumn(nameColumn, "Name");

        // Add a field updater to be notified when the user enters a new name.
        nameColumn.setFieldUpdater(new FieldUpdater<TagDto, String>() {

            @Override
            public void update(int index, TagDto object, String value) {
                eventBus.fireEvent(new LoadTagEvent(object.getId()));
            }
        });
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
