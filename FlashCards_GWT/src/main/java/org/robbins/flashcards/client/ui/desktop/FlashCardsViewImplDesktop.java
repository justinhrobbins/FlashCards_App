package org.robbins.flashcards.client.ui.desktop;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.robbins.flashcards.client.factory.ClientFactory;
import org.robbins.flashcards.client.ui.AppConstants;
import org.robbins.flashcards.client.ui.FlashCardsView;
import org.robbins.flashcards.client.ui.images.Images;
import org.robbins.flashcards.client.ui.widgets.ImageCell;
import org.robbins.flashcards.client.ui.widgets.LinkCell;
import org.robbins.flashcards.client.ui.widgets.TagListCell;
import org.robbins.flashcards.events.DeleteFlashCardEvent;
import org.robbins.flashcards.events.LoadFlashCardEvent;
import org.robbins.flashcards.events.LoadTagEvent;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.view.client.ListDataProvider;

public class FlashCardsViewImplDesktop extends CellTable<FlashCard> implements FlashCardsView {
	private final EventBus eventBus;
	private final AppConstants constants;
	
	private DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd/yyyy");
	
	private LinkCell questionCell;
	private Column<FlashCard, String> questionColumn;
	
	private TagListCell tagsCell;
	private Column<FlashCard, List<Tag>> tagsColumn;

	private ImageCell editCell;
	private Column<FlashCard, ImageResource> editColumn;

	private ImageCell deleteCell;
	private Column<FlashCard, ImageResource> deleteColumn;
	
	private DateCell dateCreatedCell;
	private Column<FlashCard, Date> dateCreatedColumn;
	
	private DateCell dateUpdatedCell;
	private Column<FlashCard, Date> dateUpdatedColumn;
	
	private ListDataProvider<FlashCard> dataProvider;
	private List<FlashCard> flashCardsList;
	
	private Images images = (Images) GWT.create(Images.class);
	
    public FlashCardsViewImplDesktop(EventBus eventBus, int pageSize, CellTable.Resources resources, ClientFactory clientFactory) {
    	super(pageSize, resources);
    	
    	GWT.log("Creating 'FlashCardsViewImplDesktop'");
    	
    	this.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
    	this.eventBus = eventBus;
    	this.constants = clientFactory.getConstants();
    	init();
    }
    
    public void init() {
    	createColumns();
        formatCellTable();
    }
    
	private void bindData(List<FlashCard> tags) {
		dataProvider = new ListDataProvider<FlashCard>();

        // Connect the table to the data provider.
        dataProvider.addDataDisplay(this);

        flashCardsList = dataProvider.getList();
        for (FlashCard tag : tags) {
        	flashCardsList.add(tag);
        }
	}

	private void formatCellTable() {
        // Set the width of the table and put the table in fixed width mode.
        this.setWidth("100%", false);
        
		// Set the width of each column.
        this.setColumnWidth(questionColumn, 50.0, Unit.PCT);
        this.setColumnWidth(tagsColumn, 30.0, Unit.PCT);
        this.setColumnWidth(editColumn, 10.0, Unit.PCT);
        this.setColumnWidth(editColumn, 10.0, Unit.PCT);
        //this.setColumnWidth(dateCreatedColumn, 10.0, Unit.PCT);
        //this.setColumnWidth(dateUpdatedColumn, 10.0, Unit.PCT);
	}

	private void createColumnSorting() {
		// Add a ColumnSortEvent.ListHandler to connect sorting to the List
        ListHandler<FlashCard> nameColumnSortHandler = new ListHandler<FlashCard>(flashCardsList);
        nameColumnSortHandler.setComparator(questionColumn,
            new Comparator<FlashCard>() {
              public int compare(FlashCard o1, FlashCard o2) {
                  if (o1 == o2) {
                      return 0;
                    }

                    // Compare the name columns.
                    if (o1 != null) {
                      return (o2 != null) ? o1.getQuestion().compareTo(o2.getQuestion()) : 1;
                    }
                    return -1;
                  }

            });
        this.addColumnSortHandler(nameColumnSortHandler);
        
        // Add a ColumnSortEvent.ListHandler to connect sorting to the List
        ListHandler<FlashCard> createdColumnSortHandler = new ListHandler<FlashCard>(flashCardsList);
        createdColumnSortHandler.setComparator(dateCreatedColumn,
            new Comparator<FlashCard>() {
              public int compare(FlashCard o1, FlashCard o2) {
                  if (o1 == o2) {
                      return 0;
                    }

                    // Compare the name columns.
                    if (o1 != null) {
                      return (o2 != null) ? o1.getCreatedDate().compareTo(o2.getCreatedDate()) : 1;
                    }
                    return -1;
                  }

            });
        this.addColumnSortHandler(createdColumnSortHandler);

        // Add a ColumnSortEvent.ListHandler to connect sorting to the List
        ListHandler<FlashCard> updatedColumnSortHandler = new ListHandler<FlashCard>(flashCardsList);
        updatedColumnSortHandler.setComparator(dateUpdatedColumn,
            new Comparator<FlashCard>() {
              public int compare(FlashCard o1, FlashCard o2) {
                  if (o1 == o2) {
                      return 0;
                    }

                    // Compare the name columns.
                    if (o1 != null) {
                      return (o2 != null) ? o1.getLastModifiedDate().compareTo(o2.getLastModifiedDate()) : 1;
                    }
                    return -1;
                  }

            });
        this.addColumnSortHandler(updatedColumnSortHandler);
        
        // We know that the data is sorted alphabetically by default.
        this.getColumnSortList().push(questionColumn);
	}

	private void createColumns() {

		questionCell = new LinkCell();
		questionColumn = new Column<FlashCard, String>(questionCell) {
          @Override
          public String getValue(FlashCard object) {
            return object.getQuestion();
          }
        };
        questionColumn.setSortable(true);
        this.addColumn(questionColumn, constants.question());
        
        // Add a field updater to be notified when the user enters a new name.
        questionColumn.setFieldUpdater(new FieldUpdater<FlashCard, String>() {
			@Override
			public void update(int index, FlashCard object, String value) {
				eventBus.fireEvent(new LoadFlashCardEvent(object.getId()));
			}
		});
        
        tagsCell = new TagListCell();
        tagsColumn = new Column<FlashCard, List<Tag>>(tagsCell) {
            @Override
            public List<Tag> getValue(FlashCard object) {
            	return object.getTagsAsList();
            }
          };
          tagsColumn.setSortable(false);
          this.addColumn(tagsColumn, constants.tags());
          
          // Add a field updater to be notified when the user enters a new name.
          tagsColumn.setFieldUpdater(new FieldUpdater<FlashCard, List<Tag>>() {
			@Override
			public void update(int index, FlashCard object, List<Tag> tagList) {
				GWT.log("update: " + tagList.get(0).getId());
				eventBus.fireEvent(new LoadTagEvent(tagList.get(0).getId()));
			}
		});

          editCell = new ImageCell();
          editColumn = new Column<FlashCard, ImageResource>(editCell) {
        	  @Override
              public void onBrowserEvent(Context context, Element elem, FlashCard object, NativeEvent event) {
        		  GWT.log("Click on Edit cell for: " + object.getId());
        		  eventBus.fireEvent(new LoadFlashCardEvent(object.getId()));
        	  }
              
        	  @Override
        	  public ImageResource getValue(FlashCard object) {
        		  return images.edit();
        	  }
          };
          editColumn.setSortable(false);
          this.addColumn(editColumn, constants.edit());

          deleteCell = new ImageCell();
          deleteColumn = new Column<FlashCard, ImageResource>(deleteCell) {
        	  @Override
              public void onBrowserEvent(Context context, Element elem, FlashCard object, NativeEvent event) {
        		  GWT.log("Click on Delete cell for: " + object.getId());
        		  eventBus.fireEvent(new DeleteFlashCardEvent(object.getId()));
        	  }
              
        	  @Override
        	  public ImageResource getValue(FlashCard object) {
        		  return images.delete();
        	  }
          };
          deleteColumn.setSortable(false);
          this.addColumn(deleteColumn, constants.delete());
          
        dateCreatedCell = new DateCell(dateFormat);
        dateCreatedColumn = new Column<FlashCard, Date>(dateCreatedCell) {
          @Override
          public Date getValue(FlashCard flashCard) {
            return flashCard.getCreatedDate().toDate();
          }
        };
        dateCreatedColumn.setSortable(true);
        //this.addColumn(dateCreatedColumn, "Date Created");

        dateUpdatedCell = new DateCell(dateFormat);
        dateUpdatedColumn = new Column<FlashCard, Date>(dateUpdatedCell) {
          @Override
          public Date getValue(FlashCard flashCard) {
            return flashCard.getLastModifiedDate().toDate();
          }
        };
        dateUpdatedColumn.setSortable(true);
        //this.addColumn(dateUpdatedColumn, "Date Updated");
	}

	@Override
	public HasClickHandlers getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setData(List<FlashCard> data) {
        bindData(data);
        
        createColumnSorting();

        // Set the total row count. This isn't strictly necessary, but it affects
        // paging calculations, so its good habit to keep the row count up to date.
        this.setRowCount(flashCardsList.size(), true);
	}

	@Override
	public int getClickedRow(LoadFlashCardEvent event) {
		// TODO Auto-generated method stub
		return 0;
	}
}
