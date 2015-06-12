package de.rtcustomz.getraenkeautomat.client;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

import de.rtcustomz.getraenkeautomat.client.proxies.CardProxy;
import de.rtcustomz.getraenkeautomat.client.proxies.UserProxy;
import de.rtcustomz.getraenkeautomat.shared.ModelRequestFactory;
import de.rtcustomz.getraenkeautomat.shared.requests.CardRequest;
import de.rtcustomz.getraenkeautomat.shared.requests.UserRequest;

public class AdminGUI implements EntryPoint {
	private final Messages messages = GWT.create(Messages.class);

	private final ModelRequestFactory requestFactory = GWT.create(ModelRequestFactory.class);
	
	//-----\/-----
	DataGrid<CardProxy> dataGrid;
	SimplePager pager;
	
    private final ListDataProvider<CardProxy> dataProvider = new ListDataProvider<CardProxy>();
    
	CardRequest request_card = requestFactory.cardRequest();
	CardProxy newCardProxy = request_card.create(CardProxy.class);
	
	UserRequest request_user = requestFactory.userRequest();
	UserProxy newUserProxy = request_user.create(UserProxy.class);
	
	final EventBus eventBus = new SimpleEventBus();
	
	final HTML serverResponseLabel = new HTML();
	//-----/\-----
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		MyResources.INSTANCE.css().ensureInjected();
		
		FlowPanel wrapper = new FlowPanel();
		FlowPanel clear = new FlowPanel();
		clear.setStyleName("clear");

		Header header = new Header();
		Navigation nav = new Navigation();
		Footer footer = new Footer();

		wrapper.getElement().setId("wrapper");
    	wrapper.add(header);
    	wrapper.add(nav);
    	wrapper.add(clear);
    	
    	FlowPanel content = new FlowPanel();
    	content.getElement().setId("content");
    	
    	// TODO: add content to contentFlowPanel, e.g.:
    	content.add(new HTMLPanel(HeadingElement.TAG_H1, "Hier kommt der Content rein!!!"));
    	
    	//-----\/-----
    	requestFactory.initialize(eventBus);
		dataGrid = new DataGrid<CardProxy>();//(KEY_PROVIDER);
		dataGrid.setWidth("100%");
		dataGrid.setHeight("300px");
		
		dataGrid.setAutoHeaderRefreshDisabled(true);
		
		dataGrid.setEmptyTableWidget(new Label("Leere Tabelle..."));
		ListHandler<CardProxy> sortHandler = new ListHandler<CardProxy>(dataProvider.getList());
		dataGrid.addColumnSortHandler(sortHandler);
		
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(dataGrid);
		
		final SelectionModel<CardProxy> selectionModel = new MultiSelectionModel<CardProxy>();
		dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager.<CardProxy> createCheckboxManager());
		
		initTableColumns(selectionModel, sortHandler);
		
		dataProvider.addDataDisplay(dataGrid);
		
		request_card.findAllCards().with("user").fire(new Receiver<List<CardProxy>>() {
			public void onSuccess(List <CardProxy> cards) {
				dataProvider.setList(cards);
				serverResponseLabel.setHTML("<p>Card[0]-ID: "+cards.get(0).getId()+"</p>");
			}
			@Override
			public void onFailure(ServerFailure error) {
				serverResponseLabel.setHTML("<p>"+error.getMessage()+"</p>");
			}
		});
		
		content.add(serverResponseLabel);
		content.add(dataGrid);
		content.add(pager);
    	//-----/\-----
    	
    	wrapper.add(content);
    	wrapper.add(footer);
    	
    	RootLayoutPanel.get().add(wrapper);
	}
	
	/**
	 * Add the columns to the table.
	 */
	private void initTableColumns(final SelectionModel<CardProxy> selectionModel,
	    ListHandler<CardProxy> sortHandler) {
	  // Checkbox column. This table will uses a checkbox column for selection.
	  // Alternatively, you can call dataGrid.setSelectionEnabled(true) to enable
	  // mouse selection.
	  Column<CardProxy, Boolean> checkColumn =
	      new Column<CardProxy, Boolean>(new CheckboxCell(true, false)) {
	        @Override
	        public Boolean getValue(CardProxy object) {
	          // Get the value from the selection model.
	          return selectionModel.isSelected(object);
	        }
	      };
	  dataGrid.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
	  dataGrid.setColumnWidth(checkColumn, 40, Unit.PX);
	
	  // id.
	  Column<CardProxy, String> idColumn =
	      new Column<CardProxy, String>(new TextCell()) {
	        @Override
	        public String getValue(CardProxy object) {
	          return object.getId();
	        }
	      };
	  idColumn.setSortable(true);
	  sortHandler.setComparator(idColumn, new Comparator<CardProxy>() {
	    @Override
	    public int compare(CardProxy o1, CardProxy o2) {
	      return o1.getId().compareTo(o2.getId());
	    }
	  });
	  dataGrid.addColumn(idColumn, "ID");
	  dataGrid.setColumnWidth(idColumn, 20, Unit.PCT);
	
	  // type.
	  Column<CardProxy, String> typeNameColumn =
	      new Column<CardProxy, String>(new TextCell()) {
	        @Override
	        public String getValue(CardProxy object) {
	          return object.getType();
	        }
	      };
	  typeNameColumn.setSortable(true);
	  sortHandler.setComparator(typeNameColumn, new Comparator<CardProxy>() {
	    @Override
	    public int compare(CardProxy o1, CardProxy o2) {
	      return o1.getType().compareTo(o2.getType());
	    }
	  });
	  dataGrid.addColumn(typeNameColumn, "Type");
	  dataGrid.setColumnWidth(typeNameColumn, 20, Unit.PCT);
	
	  // userid.
	  Column<CardProxy, Number> userColumn = new Column<CardProxy, Number>(new NumberCell()) {
	    @Override
	    public Number getValue(CardProxy object) {
	    	UserProxy user = object.getUser();
	    	if(user!=null) return user.getId();
	    	else return -1;
	    }
	  };
	  userColumn.setSortable(true);
	  sortHandler.setComparator(userColumn, new Comparator<CardProxy>() {
	    @Override
	    public int compare(CardProxy o1, CardProxy o2) {
//	    	if(o1.getUser().getId()<o2.getUser().getId())
//	    		return 1;
//	    	else
//	    		if(o1.getUser().getId()==o2.getUser().getId())
//	    			return 0;
//	    		else
//	    			return -1;
	    	return 1;
	    }
	  });
	  dataGrid.addColumn(userColumn, "UserID");
	  userColumn.setFieldUpdater(new FieldUpdater<CardProxy, Number>() {
	    @Override
	    public void update(int index, CardProxy object, Number value) {
	      // Called when the user changes the value.
	      //object.setUser(newUserProxy);
	      dataProvider.refresh();
	    }
	  });
	  dataGrid.setColumnWidth(userColumn, 7, Unit.EM);
	
	  // Description.
	  Column<CardProxy, String> descriptionColumn =
	      new Column<CardProxy, String>(new EditTextCell()) {
	        @Override
	        public String getValue(CardProxy object) {
	          return object.getDescription();
	        }
	      };
	  descriptionColumn.setSortable(true);
	  sortHandler.setComparator(descriptionColumn, new Comparator<CardProxy>() {
	    @Override
	    public int compare(CardProxy o1, CardProxy o2) {
	      return o1.getDescription().compareTo(o2.getDescription());
	    }
	  });
	  dataGrid.addColumn(descriptionColumn, "Description");
	  descriptionColumn.setFieldUpdater(new FieldUpdater<CardProxy, String>() {
	    @Override
	    public void update(int index, CardProxy object, String value) {
	      // Called when the user changes the value.
	      object.setDescription(value);
	      dataProvider.refresh();
	    }
	  });
	  dataGrid.setColumnWidth(descriptionColumn, 20, Unit.PCT);
	}
}
