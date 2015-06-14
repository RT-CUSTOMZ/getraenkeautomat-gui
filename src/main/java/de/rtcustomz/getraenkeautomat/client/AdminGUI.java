package de.rtcustomz.getraenkeautomat.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
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
    final ListDataProvider<UserProxy> userProvider = new ListDataProvider<UserProxy>();
    List<UserProxy> userlist = new ArrayList<UserProxy>();
    
	CardRequest request_card = requestFactory.cardRequest();
	CardProxy newCardProxy = request_card.create(CardProxy.class);
	
	UserRequest request_user = requestFactory.userRequest();
	UserProxy newUserProxy = request_user.create(UserProxy.class);
	
	final EventBus eventBus = new SimpleEventBus();
	
	final HTML cardLoadLabel = new HTML();
	final HTML userLoadLabel = new HTML();
	final HTML debugLabel = new HTML();
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
				//AdminGUI.this.userProvider.getList().clear();
				//AdminGUI.this.userProvider.getList().addAll(users);
				//AdminGUI.this.userlist.addAll(users);
				setUserList(users);
				//Window.alert("Eins");
				initTableColumns(selectionModel, sortHandler);
				//Window.alert("Zwei");
				showGrid();
				//userProvider.getList().clear();
				//userProvider.getList().addAll(users);
				userLoadLabel.setHTML("<p>Status: User Data loaded</p>");
			}
			@Override
			public void onFailure(ServerFailure error) {
				userLoadLabel.setHTML("<p>"+error.getMessage()+"</p>");
			}
		});
		
//		initTableColumns(selectionModel, sortHandler);
//		
//		dataProvider.addDataDisplay(dataGrid);
//		
//		request_card.findAllCards().with("user").fire(new Receiver<List<CardProxy>>() {
//			public void onSuccess(List <CardProxy> cards) {
//				//dataProvider.setList(cards);
//				dataProvider.getList().clear();
//				dataProvider.getList().addAll(cards);
//				cardLoadLabel.setHTML("<p>Status: Card Data loaded</p>");
//			}
//			@Override
//			public void onFailure(ServerFailure error) {
//				cardLoadLabel.setHTML("<p>"+error.getMessage()+"</p>");
//			}
//		});
		
		content.add(cardLoadLabel);
		content.add(userLoadLabel);
		content.add(debugLabel);
		content.add(dataGrid);
		content.add(pager);
		
    	//-----/\-----
    	
    	wrapper.add(content);
    	wrapper.add(footer);
    	
    	RootPanel.get().add(wrapper);
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
	
	// user
	    //final List<UserProxy> users = userProvider.getList();
		//Window.alert("userlist size: "+userlist.size());
	    List<String> userNames = new ArrayList<String>();
	    for (UserProxy user : userlist) {
	      userNames.add(user.getFirstname()+" "+user.getLastname());
	    }
	    SelectionCell userCell = new SelectionCell(userNames);
	    Column<CardProxy, String> userColumn = new Column<CardProxy, String>(userCell) {
	      @Override
	      public String getValue(CardProxy object) {
	    	  UserProxy user = object.getUser();
	    	  if(user!=null) return user.getFirstname()+" "+user.getLastname();
	    	  else return "";
	      }
	    };
	    dataGrid.addColumn(userColumn, "User");
	    userColumn.setFieldUpdater(new FieldUpdater<CardProxy, String>() {
	      @Override
	      public void update(int index, CardProxy object, String value) {
	        for (UserProxy user : userlist) {
	        	String name = user.getFirstname()+" "+user.getLastname();
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
	    dataGrid.setColumnWidth(userColumn, 130, Unit.PX);
	  
//	  // userid.
//	  Column<CardProxy, String> userColumn = new Column<CardProxy, String>(new EditTextCell()) {
//	    @Override
//	    public String getValue(CardProxy object) {
//	    	UserProxy user = object.getUser();
//	    	if(user!=null) return user.getId()+"";
//	    	else return "";
//	    }
//	  };
//	  userColumn.setSortable(true);
//	  sortHandler.setComparator(userColumn, new Comparator<CardProxy>() {
//	    @Override
//	    public int compare(CardProxy o1, CardProxy o2) {
////	    	if(o1.getUser().getId()<o2.getUser().getId())
////	    		return 1;
////	    	else
////	    		if(o1.getUser().getId()==o2.getUser().getId())
////	    			return 0;
////	    		else
////	    			return -1;
//	    	return 1;
//	    }
//	  });
//	  dataGrid.addColumn(userColumn, "UserID");
//	  userColumn.setFieldUpdater(new FieldUpdater<CardProxy, String>() {
//	    @Override
//	    public void update(int index, CardProxy object, String value) {
//	      // Called when the user changes the value.
//		      // Called when the user changes the value.
//	    		int id = 0;
//	    		id = Integer.parseInt(value);
//	    		if(id>0)
//	    		{
//	    		request_user = requestFactory.userRequest();
//	    		request_user.findById(Integer.parseInt(value)).fire(new Receiver<UserProxy>(){
//	    			@Override
//					public void onSuccess(UserProxy found_user) {
//	    				//newUserProxy=found_user;
//	    				userProvider.getList().add(found_user);
//	    				//Window.alert("userlist size: "+userProvider.getList().size());
//					}
//
//					@Override
//					public void onFailure(ServerFailure error) {
//						// TODO Auto-generated method stub
//						Window.alert("Error:\n"+error.getMessage());
//					}
//	    		});
//	    		//Window.alert("userlist size: "+userProvider.getList().size());
//	    		//Window.alert("id: "+userProvider.getList().get(0).getId()+"\n fname: "+userProvider.getList().get(0).getFirstname());
//	    		request_card = requestFactory.cardRequest();
//		    	CardProxy card = request_card.edit(object);
//		    	card.setUser(userProvider.getList().get(0));
//		    	request_card.save(card).with("user").fire(new Receiver<Void>(){
//					@Override
//					public void onSuccess(Void arg0) {
//						dataProvider.refresh();
//					}
//		    		
//		    	});
//		    	}
//	    		else
//	    		{
//	    			request_card = requestFactory.cardRequest();
//			    	CardProxy card = request_card.edit(object);
//			    	card.setUser(null);
//			    	request_card.save(card).fire(new Receiver<Void>(){
//						@Override
//						public void onSuccess(Void arg0) {
//							dataProvider.refresh();
//						}
//			    		
//			    	});
//	    		}
//		    }
//	  });
//	  dataGrid.setColumnWidth(userColumn, 7, Unit.EM);
	
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
	  dataGrid.setColumnWidth(descriptionColumn, 20, Unit.PCT);
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
}
