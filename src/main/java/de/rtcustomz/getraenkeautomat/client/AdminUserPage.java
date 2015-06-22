package de.rtcustomz.getraenkeautomat.client;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
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

import de.rtcustomz.getraenkeautomat.client.proxies.UserProxy;
import de.rtcustomz.getraenkeautomat.shared.ModelRequestFactory;
import de.rtcustomz.getraenkeautomat.shared.requests.UserRequest;

public class AdminUserPage extends Page {
	
	static private AdminUserPage _instance = null;
	private static final String pageName = "Benutzer";
	
	private final ModelRequestFactory requestFactory = GWT.create(ModelRequestFactory.class);
	
	//-----\/-----
	DataGrid<UserProxy> dataGrid;
	SimplePager pager;
	
    private final ListDataProvider<UserProxy> dataProvider = new ListDataProvider<UserProxy>();
    
	UserRequest request_user = requestFactory.userRequest();
	UserProxy newUserProxy = request_user.create(UserProxy.class);
	
	final EventBus eventBus = new SimpleEventBus();
	
	final HTML userLoadLabel = new HTML();
	final HTML debugLabel = new HTML();
	//-----/\-----
	
	public AdminUserPage() 
	{
        initPage();
        initWidget(page);
	}
	
    public static AdminUserPage getInstance(){
        if(null == _instance) {
            _instance = new AdminUserPage();
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
		dataGrid = new DataGrid<UserProxy>();//(KEY_PROVIDER);
		dataGrid.setWidth("100%");
		dataGrid.setHeight("300px");
		
		//dataGrid.setAutoHeaderRefreshDisabled(true);
		
		dataGrid.setEmptyTableWidget(new Label("Leere Tabelle..."));
		final ListHandler<UserProxy> sortHandler = new ListHandler<UserProxy>(dataProvider.getList());
		dataGrid.addColumnSortHandler(sortHandler);
		
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(dataGrid);
		
		final SelectionModel<UserProxy> selectionModel = new MultiSelectionModel<UserProxy>();
		dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager.<UserProxy> createCheckboxManager());
		
		/*request_user.findAllUsers().fire(new Receiver<List<UserProxy>>() {
			@Override
			public void onSuccess(List<UserProxy> users) {
				dataProvider.getList().clear();
				dataProvider.getList().addAll(users);
				userLoadLabel.setHTML("<p>Status: User Data loaded</p>");
			}
			@Override
			public void onFailure(ServerFailure error) {
				userLoadLabel.setHTML("<p>"+error.getMessage()+"</p>");
			}
		});*/

		initTableColumns(selectionModel, sortHandler);
		
		showGrid();
		
		page.add(userLoadLabel);
		page.add(debugLabel);
		page.add(dataGrid);
		page.add(pager);
		
    	//-----/\-----
	}
	
	/**
	 * Add the columns to the table.
	 */
	private void initTableColumns(final SelectionModel<UserProxy> selectionModel,
	    ListHandler<UserProxy> sortHandler) {
//	  // Checkbox column. This table will uses a checkbox column for selection.
//	  // Alternatively, you can call dataGrid.setSelectionEnabled(true) to enable
//	  // mouse selection.
//	  Column<UserProxy, Boolean> checkColumn =
//	      new Column<UserProxy, Boolean>(new CheckboxCell(true, false)) {
//	        @Override
//	        public Boolean getValue(UserProxy object) {
//	          // Get the value from the selection model.
//	          return selectionModel.isSelected(object);
//	        }
//	      };
//	  dataGrid.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
//	  dataGrid.setColumnWidth(checkColumn, 40, Unit.PX);
//		final List<UserProxy> card_list = new ArrayList<UserProxy>();
		
	  // id.
	  Column<UserProxy, String> idColumn =
	      new Column<UserProxy, String>(new TextCell()) {
	        @Override
	        public String getValue(UserProxy object) {
	          return object.getId()+"";
	        }
	      };
	  idColumn.setSortable(true);
	  sortHandler.setComparator(idColumn, new Comparator<UserProxy>() {
	    @Override
	    public int compare(UserProxy o1, UserProxy o2) {
	      return Integer.compare(o1.getId(), o2.getId());
	    }
	  });
	  dataGrid.addColumn(idColumn, "ID");
	  //dataGrid.setColumnWidth(idColumn, 140, Unit.PX);
	  dataGrid.setColumnWidth(idColumn, 17, Unit.PCT);
		
	  // firstname.
	  Column<UserProxy, String> firstNameColumn =
	      new Column<UserProxy, String>(new EditTextCell()) {
	        @Override
	        public String getValue(UserProxy object) {
	          return object.getFirstname();
	        }
	      };
	      firstNameColumn.setSortable(true);
	  sortHandler.setComparator(firstNameColumn, new Comparator<UserProxy>() {
	    @Override
	    public int compare(UserProxy o1, UserProxy o2) {
	      return o1.getFirstname().compareTo(o2.getFirstname());
	    }
	  });
	  dataGrid.addColumn(firstNameColumn, "Vorname");
	  firstNameColumn.setFieldUpdater(new FieldUpdater<UserProxy, String>() {
	    @Override
	    public void update(int index, UserProxy object, String value) {
	      // Called when the user changes the value.
	    	request_user = requestFactory.userRequest();
	    	UserProxy user = request_user.edit(object);
	    	user.setFirstname(value);
	    	request_user.save(user).fire(new Receiver<Void>(){
				@Override
				public void onSuccess(Void arg0) {
					dataProvider.refresh();
				}
	    		
	    	});
	    }
	  });
	  //dataGrid.setColumnWidth(firstNameColumn, 330, Unit.PX);
	  dataGrid.setColumnWidth(firstNameColumn, 33, Unit.PCT);
	  
	  // lastname.
	  Column<UserProxy, String> lastNameColumn =
	      new Column<UserProxy, String>(new EditTextCell()) {
	        @Override
	        public String getValue(UserProxy object) {
	          return object.getLastname();
	        }
	      };
	      lastNameColumn.setSortable(true);
	  sortHandler.setComparator(lastNameColumn, new Comparator<UserProxy>() {
	    @Override
	    public int compare(UserProxy o1, UserProxy o2) {
	      return o1.getLastname().compareTo(o2.getLastname());
	    }
	  });
	  dataGrid.addColumn(lastNameColumn, "Nachname");
	  lastNameColumn.setFieldUpdater(new FieldUpdater<UserProxy, String>() {
	    @Override
	    public void update(int index, UserProxy object, String value) {
	      // Called when the user changes the value.
	    	request_user = requestFactory.userRequest();
	    	UserProxy user = request_user.edit(object);
	    	user.setLastname(value);
	    	request_user.save(user).fire(new Receiver<Void>(){
				@Override
				public void onSuccess(Void arg0) {
					dataProvider.refresh();
				}
	    		
	    	});
	    }
	  });
	  //dataGrid.setColumnWidth(descriptionColumn, 330, Unit.PX);
	  dataGrid.setColumnWidth(lastNameColumn, 33, Unit.PCT);
	  
	  // nickname.
	  Column<UserProxy, String> nickNameColumn =
	      new Column<UserProxy, String>(new EditTextCell()) {
	        @Override
	        public String getValue(UserProxy object) {
	        	String nickname = object.getNickname();
		        if(nickname!=null) 
		        	return nickname;
		        else return "";
	        }
	      };
	      nickNameColumn.setSortable(true);
	  sortHandler.setComparator(nickNameColumn, new Comparator<UserProxy>() {
	    @Override
	    public int compare(UserProxy o1, UserProxy o2) {
	      return o1.getNickname().compareTo(o2.getNickname());
	    }
	  });
	  dataGrid.addColumn(nickNameColumn, "Spitzname");
	  nickNameColumn.setFieldUpdater(new FieldUpdater<UserProxy, String>() {
	    @Override
	    public void update(int index, UserProxy object, String value) {
	      // Called when the user changes the value.
	    	request_user = requestFactory.userRequest();
	    	UserProxy user = request_user.edit(object);
	    	user.setNickname(value);
	    	request_user.save(user).fire(new Receiver<Void>(){
				@Override
				public void onSuccess(Void arg0) {
					dataProvider.refresh();
				}
	    		
	    	});
	    }
	  });
	  //dataGrid.setColumnWidth(descriptionColumn, 330, Unit.PX);
	  dataGrid.setColumnWidth(nickNameColumn, 33, Unit.PCT);	  
	}
	
	private void showGrid()
	{
		if(dataProvider.getDataDisplays().size()!=0)return;
		dataProvider.addDataDisplay(dataGrid);
		
		request_user.findAllUsers().with("user").fire(new Receiver<List<UserProxy>>() {
			public void onSuccess(List <UserProxy> cards) {
				//dataProvider.setList(cards);
				dataProvider.getList().clear();
				dataProvider.getList().addAll(cards);
				userLoadLabel.setHTML("<p>Status: Card Data loaded</p>");
			}
			@Override
			public void onFailure(ServerFailure error) {
				userLoadLabel.setHTML("<p>"+error.getMessage()+"</p>");
			}
		});
	}
}
