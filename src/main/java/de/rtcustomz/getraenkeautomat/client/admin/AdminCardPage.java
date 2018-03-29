package de.rtcustomz.getraenkeautomat.client.admin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
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

public class AdminCardPage extends AdminPage {

	/**
	 * A pending change to a {@link CardProxy}. Changes aren't committed
	 * immediately to illustrate that cells can remember their pending changes.
	 * 
	 * @param <T>
	 *            the data type being changed
	 */
	private abstract static class PendingChange<T> {
		private final CardProxy card;
		private final T value;

		public PendingChange(CardProxy card, T value) {
			this.card = card;
			this.value = value;
		}

		/**
		 * Commit the change to the contact.
		 */
		public void commit() {
			doCommit(card, value);
		}

		/**
		 * Update the appropriate field in the {@link ContactInfo}.
		 * 
		 * @param card
		 *            the contact to update
		 * @param value
		 *            the new value
		 */
		protected abstract void doCommit(CardProxy card, T value);
	}

	/**
	 * Updates the card description.
	 */
	private static class DescriptionChange extends PendingChange<String> {

		public DescriptionChange(CardProxy card, String value) {
			super(card, value);
		}

		@Override
		protected void doCommit(CardProxy card, String value) {
			//request_card = requestFactory.cardRequest();
			CardProxy newcard = request_card.edit(card);
			newcard.setDescription(value);
			request_card.save(newcard).to(new Receiver<Void>() {
				@Override
				public void onSuccess(Void arg0) {
				}

				@Override
				public void onFailure(ServerFailure error) {
					errorFlag = true;
					errorString = error.getMessage();
				}
			});
		}
	}

	/**
	 * Updates the card description.
	 */
	private static class UserChange extends PendingChange<String> {

		public UserChange(CardProxy card, String value) {
			super(card, value);
		}

		@Override
		protected void doCommit(CardProxy card, String value) {
			// set user null
			if (value.equals("-")) {
				//request_card = requestFactory.cardRequest();
				CardProxy newcard = request_card.edit(card);
				newcard.setUser(null);
				request_card.save(newcard).with("user").to(new Receiver<Void>() {
					@Override
					public void onSuccess(Void arg0) {
					}

					@Override
					public void onFailure(ServerFailure error) {
						errorFlag = true;
						errorString = error.getMessage();
					}
				});
			}
			// set user
			else {
				for (UserProxy user : userlist) {
					String name = getUserDisplayName(user);
					if (name.equals(value)) {
						//request_card = requestFactory.cardRequest();
						CardProxy newcard = request_card.edit(card);
						newcard.setUser(user);
						request_card.save(newcard).with("user").to(new Receiver<Void>() {
							@Override
							public void onSuccess(Void arg0) {
							}

							@Override
							public void onFailure(ServerFailure error) {
								errorFlag = true;
								errorString = error.getMessage();
							}
						});
					}
				}
			}
		}
	}

	static private AdminCardPage _instance = null;
	private static final String pageName = "Karten";

	private final static ModelRequestFactory requestFactory = GWT.create(ModelRequestFactory.class);
	static CardRequest request_card = requestFactory.cardRequest();
	UserRequest request_user = requestFactory.userRequest();

	final EventBus eventBus = new SimpleEventBus();

	DataGrid<CardProxy> dataGrid;
	SimplePager pager;

	private final static ListDataProvider<CardProxy> dataProvider = new ListDataProvider<CardProxy>();
	private List<PendingChange<?>> pendingChanges = new ArrayList<PendingChange<?>>();
	final SelectionModel<CardProxy> selectionModel = new MultiSelectionModel<CardProxy>();
	static List<UserProxy> userlist = new ArrayList<UserProxy>();

	final HTML cardLoadLabel = new HTML();
	final HTML userLoadLabel = new HTML();
	final Button save = new Button("Speichern");
	final static DialogBox save_dbox = new DialogBox(true);
	final static HTML saveStatusLabel = new HTML();

	final Button delete = new Button("Löschmodus");
	final static DialogBox delete_dbox = new DialogBox(true);
	final static HTML deleteStatusLabel = new HTML();
	
	final Button mark = new Button("Karten ohne User markieren");

	static boolean errorFlag = false;
	static String errorString;

	static boolean errorFlagDelete = false;
	static String errorStringDelete;

	public AdminCardPage() {
		initPage();
		initWidget(page);
	}

	public static AdminCardPage getInstance() {
		if (null == _instance) {
			_instance = new AdminCardPage();
		}
		return _instance;
	}

	public static String getPageName() {
		return pageName;
	}

	@Override
	public void initPage() {
		requestFactory.initialize(eventBus);
		dataGrid = new DataGrid<CardProxy>();// (KEY_PROVIDER);
		dataGrid.setWidth("100%");
		dataGrid.setHeight("300px");

		// dataGrid.setAutoHeaderRefreshDisabled(true);

		dataGrid.setEmptyTableWidget(new Label("Leere Tabelle..."));
		final ListHandler<CardProxy> sortHandler = new ListHandler<CardProxy>(dataProvider.getList());
		dataGrid.addColumnSortHandler(sortHandler);

		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(dataGrid);

		dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager.<CardProxy> createCheckboxManager());

		initSaveDialog();
		initDeleteDialog();

		// initTableColumns(sortHandler); moved to user request success

		// showGrid(); moved to user request success

		save.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Commit the changes
				request_card = requestFactory.cardRequest();
				for (PendingChange<?> pendingChange : pendingChanges) {
					pendingChange.commit();
				}
				pendingChanges.clear();
				if (request_card.isChanged()) {
					request_card.fire(new Receiver<Void>() {
						@Override
						public void onSuccess(Void arg0) {
							if (errorFlag)
								saveStatusLabel.setHTML("<p>Datenbankfehler: " + errorString + "</p>");
							else {
								saveStatusLabel.setHTML("<p>Speichern erfolgreich!</p>");
							}
							save.removeFromParent();
							save_dbox.center();
						}

						@Override
						public void onFailure(ServerFailure error) {
							saveStatusLabel.setHTML("<p>Serverfehler: " + error.getMessage() + "</p>");
							save.removeFromParent();
							save_dbox.center();
						}
					});
				} else {
					saveStatusLabel.setHTML("<p>Keine Änderung erkannt</p>");
					save.removeFromParent();
					save_dbox.center();
				}
				
				// Push the changes to the views.
				dataProvider.refresh();
			}
		});

		delete.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				deleteStatusLabel.setText("");
				if (dataGrid.getColumnCount() < 5) {
					setDeleteColumn(true);
					delete.setText("Auswahl löschen");
					mark.setText("Karten ohne User markieren");
					mark.setVisible(true);
				} else {
					delete_dbox.center();
				}
			}
		});
		
		mark.setVisible(false);
		mark.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (dataGrid.getColumnCount() < 5) {
					//Ignore clicks without delete mode
				} else {
					if(mark.getText()=="Karten ohne User markieren"){
						mark.setText("Markierung zurücksetzen");
						for (final CardProxy card : dataProvider.getList()) {
							if(card.getUser() == null){
								selectionModel.setSelected(card, true);
							}
						}
					}else{
						mark.setText("Karten ohne User markieren");
						for (final CardProxy card : dataProvider.getList()) {
							selectionModel.setSelected(card, false);
						}
					}
				}
			}
		});

		request_user.findAllUsers().fire(new Receiver<List<UserProxy>>() {
			@Override
			public void onSuccess(List<UserProxy> users) {
				setUserList(users);
				initTableColumns(sortHandler);
				showGrid();
				userLoadLabel.setHTML("<p>Status: User Data loaded</p>");
			}

			@Override
			public void onFailure(ServerFailure error) {
				userLoadLabel.setHTML("<p>" + error.getMessage() + "</p>");
			}
		});

		page.add(cardLoadLabel);
		page.add(userLoadLabel);
		page.add(dataGrid);
		page.add(pager);
		page.add(delete);
		page.add(mark);
	}

	/**
	 * Add the columns to the table.
	 */
	private void initTableColumns(ListHandler<CardProxy> sortHandler) {
		// card id column
		Column<CardProxy, String> idColumn = new Column<CardProxy, String>(new TextCell()) {
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
		dataGrid.setColumnWidth(idColumn, 17, Unit.PCT);

		// card type column
		Column<CardProxy, String> typeNameColumn = new Column<CardProxy, String>(new TextCell()) {
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
		dataGrid.setColumnWidth(typeNameColumn, 17, Unit.PCT);

		// description column
		Column<CardProxy, String> descriptionColumn = new Column<CardProxy, String>(new EditTextCell()) {
			@Override
			public String getValue(CardProxy object) {
				String description = object.getDescription();
				if (description != null)
					return description;
				else
					return "";
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
				if (!save.isAttached())
					page.add(save);
				pendingChanges.add(new DescriptionChange(object, value));

			}
		});
		dataGrid.setColumnWidth(descriptionColumn, 33, Unit.PCT);

		// user column
		List<String> userNames = new ArrayList<String>();
		userNames.add("-");
		for (UserProxy user : userlist) {
			userNames.add(getUserDisplayName(user));
		}
		SelectionCell userCell = new SelectionCell(userNames);
		Column<CardProxy, String> userColumn = new Column<CardProxy, String>(userCell) {
			@Override
			public String getValue(CardProxy object) {
				UserProxy user = object.getUser();
				return getUserDisplayName(user);
			}
		};
		userColumn.setSortable(true);
		sortHandler.setComparator(userColumn, new Comparator<CardProxy>() {
			@Override
			public int compare(CardProxy o1, CardProxy o2) {
				// return o1.getDescription().compareTo(o2.getDescription());
				// return
				// getUserDisplayName(o1.getUser()).compareTo(getUserDisplayName(o2.getUser()));
				UserProxy user1 = o1.getUser();
				UserProxy user2 = o2.getUser();
				if (user1 == null) {
					if (user2 == null) {
						return 0;
					}
					return -1;
				}
				if (user2 == null) {
					return 1;
				}
				return Integer.compare(user1.getId(), user2.getId());
			}
		});
		dataGrid.addColumn(userColumn, "User");
		userColumn.setFieldUpdater(new FieldUpdater<CardProxy, String>() {
			@Override
			public void update(int index, CardProxy object, String value) {
				if (!save.isAttached())
					page.add(save);
				pendingChanges.add(new UserChange(object, value));
			}
		});
		dataGrid.setColumnWidth(userColumn, 33, Unit.PCT);
	}

	private void setDeleteColumn(boolean show) {
		// Checkbox column. This table will uses a checkbox column for
		// selection.
		// Alternatively, you can call dataGrid.setSelectionEnabled(true) to
		// enable mouse selection.
		if (show) {
			Column<CardProxy, Boolean> checkColumn = new Column<CardProxy, Boolean>(new CheckboxCell(true, false)) {
				@Override
				public Boolean getValue(CardProxy object) {
					// Get the value from the selection model.
					return selectionModel.isSelected(object);
				}
			};
			dataGrid.insertColumn(0, checkColumn);// Column(checkColumn,
													// SafeHtmlUtils.fromSafeConstant("<br/>"));
			dataGrid.setColumnWidth(checkColumn, 40, Unit.PX);
		} else {
			if (dataGrid.getColumnCount() > 4) {
				dataGrid.removeColumn(0);
			}
		}
	}

	public void showGrid() {
		if (dataProvider.getDataDisplays().size() != 0)
			return;
		dataProvider.addDataDisplay(dataGrid);

		request_card.findAllCards().with("user").fire(new Receiver<List<CardProxy>>() {
			public void onSuccess(List<CardProxy> cards) {
				dataProvider.getList().clear();
				dataProvider.getList().addAll(cards);
				cardLoadLabel.setHTML("<p>Status: Card Data loaded</p>");
			}

			@Override
			public void onFailure(ServerFailure error) {
				cardLoadLabel.setHTML("<p>" + error.getMessage() + "</p>");
			}
		});
	}

	private void setUserList(List<UserProxy> users) {
		userlist.clear();
		userlist.addAll(users);
	}

	private static String getUserDisplayName(UserProxy user) {
		if (user != null)
			return user.getId() + " - " + user.getFirstname().substring(0, 15) + " " + user.getLastname().substring(0, 15);
		else
			return "-";
	}

	private void deleteCard() {
		CardRequest card_req = requestFactory.cardRequest();
		for (final CardProxy card : dataProvider.getList()) {
			if (selectionModel.isSelected(card)) {
				card_req.delete(card).to(new Receiver<Void>() {
					@Override
					public void onSuccess(Void arg0) {
						dataProvider.getList().remove(card);
					}

					@Override
					public void onFailure(ServerFailure error) {
						errorFlagDelete = true;
						errorStringDelete = error.getMessage();
					}
				});
			}
		}
		card_req.fire(new Receiver<Void>() {
			@Override
			public void onSuccess(Void arg0) {
				if (errorFlagDelete)
					deleteStatusLabel.setHTML("<p>Datenbankfehler: " + errorStringDelete + "</p>");
				else {
					delete.setText("Löschmodus");
					setDeleteColumn(false);
					mark.setText("Karten ohne User markieren");
					mark.setVisible(false);
					delete_dbox.hide();
				}
			}

			@Override
			public void onFailure(ServerFailure error) {
				saveStatusLabel.setHTML("<p>Serverfehler: " + error.getMessage() + "</p>");
			}
		});
	}

	private void initSaveDialog() {
		// --save
		final FlowPanel dialogpanel = new FlowPanel();
		final Button ok = new Button("OK");
		ok.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				save_dbox.hide();
			}
		});
		dialogpanel.add(saveStatusLabel);
		dialogpanel.add(ok);
		dialogpanel.setSize("200px", "80px");
		save_dbox.setAutoHideOnHistoryEventsEnabled(true);
		save_dbox.setText("Daten Speichern ...");
		save_dbox.setWidget(dialogpanel);
	}

	private void initDeleteDialog() {
		final FlowPanel delpanel = new FlowPanel();
		final Button add_ok = new Button("Ja");
		final Button add_cancel = new Button("Nein");
		final HTML question = new HTML("<p>Wirklich löschen? </p>");
		deleteStatusLabel.setText("");
		add_ok.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				deleteCard();
			}
		});
		add_cancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				delete_dbox.hide();
			}
		});

		delpanel.add(question);
		delpanel.add(deleteStatusLabel);
		delpanel.add(add_ok);
		delpanel.add(add_cancel);
		delpanel.setSize("200px", "100px");

		delete_dbox.setAutoHideOnHistoryEventsEnabled(true);
		delete_dbox.setText("Benutzer löschen");
		delete_dbox.setWidget(delpanel);
	}
}
