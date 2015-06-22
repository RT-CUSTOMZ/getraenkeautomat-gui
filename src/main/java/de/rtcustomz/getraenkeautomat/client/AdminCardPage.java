package de.rtcustomz.getraenkeautomat.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
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

public class AdminCardPage extends Page {
	
	static private AdminCardPage _instance = null;
	private static final String pageName = "Karten";

	private final ModelRequestFactory requestFactory = GWT.create(ModelRequestFactory.class);
	
	DataGrid<CardProxy> dataGrid;
	SimplePager pager;
	
    private final ListDataProvider<CardProxy> dataProvider = new ListDataProvider<CardProxy>();
    //final ListDataProvider<UserProxy> userProvider = new ListDataProvider<UserProxy>();
    List<UserProxy> userlist = new ArrayList<UserProxy>();
    
	CardRequest request_card = requestFactory.cardRequest();
	CardProxy newCardProxy = request_card.create(CardProxy.class);
	
	UserRequest request_user = requestFactory.userRequest();
	UserProxy newUserProxy = request_user.create(UserProxy.class);
	
	final EventBus eventBus = new SimpleEventBus();
	
	final HTML cardLoadLabel = new HTML();
	final HTML userLoadLabel = new HTML();
	
	public AdminCardPage() 
	{
        initPage();
        initWidget(page);
	}
	
    public static AdminCardPage getInstance(){
        if(null == _instance) {
            _instance = new AdminCardPage();
        }
        return _instance;
    }
    
    public static String getPageName()
    {
    	return pageName;
    }
	
	@Override
	public void initPage() {
    	
    	//-----\/-----
    	requestFactory.initialize(eventBus);
		dataGrid = new DataGrid<CardProxy>();//(KEY_PROVIDER);
		dataGrid.setWidth("100%");
		dataGrid.setHeight("300px");
		
		//dataGrid.setAutoHeaderRefreshDisabled(true);
		
		dataGrid.setEmptyTableWidget(new Label("Leere Tabelle..."));
		final ListHandler<CardProxy> sortHandler = new ListHandler<CardProxy>(dataProvider.getList());
		dataGrid.addColumnSortHandler(sortHandler);
		
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(dataGrid);
		
		final SelectionModel<CardProxy> selectionModel = new MultiSelectionModel<CardProxy>();
		dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager.<CardProxy> createCheckboxManager());
		
		request_user.findAllUsers().fire(new Receiver<List<UserProxy>>() {
			@Override
			public void onSuccess(List<UserProxy> users) {
				setUserList(users);
				initTableColumns(selectionModel, sortHandler);
				showGrid();
				userLoadLabel.setHTML("<p>Status: User Data loaded</p>");
			}
			@Override
			public void onFailure(ServerFailure error) {
				userLoadLabel.setHTML("<p>"+error.getMessage()+"</p>");
			}
		});
		
		page.add(cardLoadLabel);
		page.add(userLoadLabel);
		page.add(dataGrid);
		page.add(pager);
		
    	//-----/\-----
	}
	
	/**
	 * Add the columns to the table.
	 */
	private void initTableColumns(final SelectionModel<CardProxy> selectionModel,
	    ListHandler<CardProxy> sortHandler) {
//	  // Checkbox column. This table will uses a checkbox column for selection.
//	  // Alternatively, you can call dataGrid.setSelectionEnabled(true) to enable
//	  // mouse selection.
//	  Column<CardProxy, Boolean> checkColumn =
//	      new Column<CardProxy, Boolean>(new CheckboxCell(true, false)) {
//	        @Override
//	        public Boolean getValue(CardProxy object) {
//	          // Get the value from the selection model.
//	          return selectionModel.isSelected(object);
//	        }
//	      };
//	  dataGrid.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
//	  dataGrid.setColumnWidth(checkColumn, 40, Unit.PX);
//		final List<CardProxy> card_list = new ArrayList<CardProxy>();
		
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
	  //dataGrid.setColumnWidth(idColumn, 140, Unit.PX);
	  dataGrid.setColumnWidth(idColumn, 17, Unit.PCT);
	
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
	  //dataGrid.setColumnWidth(typeNameColumn, 140, Unit.PX);
	  dataGrid.setColumnWidth(typeNameColumn, 17, Unit.PCT);
		
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
	    	request_card = requestFactory.cardRequest();
	    	CardProxy card = request_card.edit(object);
	    	card.setDescription(value);
	    	request_card.save(card).fire(new Receiver<Void>(){
				@Override
				public void onSuccess(Void arg0) {
					dataProvider.refresh();
				}
	    		
	    	});
	    }
	  });
	  //dataGrid.setColumnWidth(descriptionColumn, 330, Unit.PX);
	  dataGrid.setColumnWidth(descriptionColumn, 33, Unit.PCT);
	  
	  // user
	    //final List<UserProxy> users = userProvider.getList();
		//Window.alert("userlist size: "+userlist.size());
	    List<String> userNames = new ArrayList<String>();
	    userNames.add("-");
	    for (UserProxy user : userlist) {
	      //userNames.add(user.getFirstname()+" "+user.getLastname());
	    	userNames.add(getUserDisplayName(user));
	    }
	    SelectionCell userCell = new SelectionCell(userNames);
	    Column<CardProxy, String> userColumn = new Column<CardProxy, String>(userCell) {
	      @Override
	      public String getValue(CardProxy object) {
	    	  UserProxy user = object.getUser();
	    	  //if(user!=null) return user.getFirstname()+" "+user.getLastname();
	    	  //else return "";
	    	  return getUserDisplayName(user);
	      }
	    };
	    dataGrid.addColumn(userColumn, "User");
	    userColumn.setFieldUpdater(new FieldUpdater<CardProxy, String>() {
	      @Override
	      public void update(int index, CardProxy object, String value) {
	        for (UserProxy user : userlist) {
	        	//String name = user.getFirstname()+" "+user.getLastname();
	        	String name = getUserDisplayName(user);
	        	if (name.equals(value)) {
	        		request_card = requestFactory.cardRequest();
	        		CardProxy card = request_card.edit(object);
			    	card.setUser(user);
			    	request_card.save(card).with("user").fire(new Receiver<Void>(){
						@Override
						public void onSuccess(Void arg0) {
							dataProvider.refresh();
						}
			    		
			    	});
			    }
	        }
	      }
	    });
	    //dataGrid.setColumnWidth(userColumn, 310, Unit.PX);
	    dataGrid.setColumnWidth(userColumn, 33, Unit.PCT);
	}
	
	private void showGrid()
	{
		if(dataProvider.getDataDisplays().size()!=0)return;
		dataProvider.addDataDisplay(dataGrid);
		
		request_card.findAllCards().with("user").fire(new Receiver<List<CardProxy>>() {
			public void onSuccess(List <CardProxy> cards) {
				//dataProvider.setList(cards);
				dataProvider.getList().clear();
				dataProvider.getList().addAll(cards);
				cardLoadLabel.setHTML("<p>Status: Card Data loaded</p>");
			}
			@Override
			public void onFailure(ServerFailure error) {
				cardLoadLabel.setHTML("<p>"+error.getMessage()+"</p>");
			}
		});
	}
	
	private void setUserList(List<UserProxy> users)
	{
		userlist.clear();
		userlist.addAll(users);
	}
	
	private String getUserDisplayName(UserProxy user)
	{
  	  if(user!=null) return user.getId() + " - " + user.getFirstname().substring(0, 15) + " " + user.getLastname().substring(0, 15);
  	  else return "";
	}
}
