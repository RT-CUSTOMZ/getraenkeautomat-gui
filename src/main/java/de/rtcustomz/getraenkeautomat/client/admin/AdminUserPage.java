package de.rtcustomz.getraenkeautomat.client.admin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
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
import com.google.gwt.user.client.ui.TextBox;
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

public class AdminUserPage extends AdminPage {

	/**
	 * A pending change to a {@link UserProxy}. Changes aren't committed
	 * immediately to illustrate that cells can remember their pending changes.
	 * 
	 * @param <T>
	 *            the data type being changed
	 */
	private abstract static class PendingChange<T> {
		private final UserProxy user;
		private final T value;

		public PendingChange(UserProxy user, T value) {
			this.user = user;
			this.value = value;
		}

		/**
		 * Commit the change to the contact.
		 */
		public void commit() {
			doCommit(user, value);
		}

		/**
		 * Update the appropriate field in the {@link ContactInfo}.
		 * 
		 * @param slot
		 *            the contact to update
		 * @param value
		 *            the new value
		 */
		protected abstract void doCommit(UserProxy user, T value);
	}

	/**
	 * Updates the first name.
	 */
	private static class FirstNameChange extends PendingChange<String> {

		public FirstNameChange(UserProxy user, String value) {
			super(user, value);
		}

		@Override
		protected void doCommit(UserProxy user, String value) {
			request_user = requestFactory.userRequest();
			UserProxy newuser = request_user.edit(user);
			newuser.setFirstname(value);
			request_user.save(newuser).to(new Receiver<Void>() {
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
	 * Updates the last name.
	 */
	private static class LastNameChange extends PendingChange<String> {

		public LastNameChange(UserProxy user, String value) {
			super(user, value);
		}

		@Override
		protected void doCommit(UserProxy user, String value) {
			request_user = requestFactory.userRequest();
			UserProxy newuser = request_user.edit(user);
			newuser.setLastname(value);
			request_user.save(newuser).to(new Receiver<Void>() {
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
	 * Updates the nick name.
	 */
	private static class NickNameChange extends PendingChange<String> {

		public NickNameChange(UserProxy user, String value) {
			super(user, value);
		}

		@Override
		protected void doCommit(UserProxy user, String value) {
			request_user = requestFactory.userRequest();
			UserProxy newuser = request_user.edit(user);
			newuser.setNickname(value);
			request_user.save(newuser).to(new Receiver<Void>() {
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

	static private AdminUserPage _instance = null;
	private static final String pageName = "Benutzer";

	private final static ModelRequestFactory requestFactory = GWT.create(ModelRequestFactory.class);
	static UserRequest request_user = requestFactory.userRequest();
	// UserProxy newUserProxy = request_user.create(UserProxy.class);

	final EventBus eventBus = new SimpleEventBus();

	DataGrid<UserProxy> dataGrid;
	SimplePager pager;

	private final ListDataProvider<UserProxy> dataProvider = new ListDataProvider<UserProxy>();
	private List<PendingChange<?>> pendingChanges = new ArrayList<PendingChange<?>>();
	final SelectionModel<UserProxy> selectionModel = new MultiSelectionModel<UserProxy>();

	final HTML userLoadLabel = new HTML();
	final Button save = new Button("Speichern");
	final static DialogBox save_dbox = new DialogBox(true);
	final static HTML saveStatusLabel = new HTML();

	final Button add = new Button("Neuer Benutzer");
	final static DialogBox add_dbox = new DialogBox(true);
	final static HTML addStatusLabel = new HTML();

	final Button delete = new Button("Löschmodus");
	final static DialogBox delete_dbox = new DialogBox(true);
	final static HTML deleteStatusLabel = new HTML();

	static boolean errorFlag = false;
	static String errorString;

	static boolean errorFlagDelete = false;
	static String errorStringDelete;

	public AdminUserPage() {
		initPage();
		initWidget(page);
	}

	public static AdminUserPage getInstance() {
		if (null == _instance) {
			_instance = new AdminUserPage();
		}
		return _instance;
	}

	public static String getPageName() {
		return pageName;
	}

	@Override
	public void initPage() {
		requestFactory.initialize(eventBus);
		dataGrid = new DataGrid<UserProxy>();
		dataGrid.setWidth("100%");
		dataGrid.setHeight("300px");

		// dataGrid.setAutoHeaderRefreshDisabled(true);

		dataGrid.setEmptyTableWidget(new Label("Leere Tabelle..."));
		final ListHandler<UserProxy> sortHandler = new ListHandler<UserProxy>(dataProvider.getList());
		dataGrid.addColumnSortHandler(sortHandler);

		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(dataGrid);

		dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager.<UserProxy> createCheckboxManager());

		initSaveDialog();
		// initAddDialog(); -- moved to clickhandler
		initDeleteDialog();

		initTableColumns(sortHandler);

		showGrid();

		save.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Commit the changes
				request_user = requestFactory.userRequest();
				for (PendingChange<?> pendingChange : pendingChanges) {
					pendingChange.commit();
				}
				pendingChanges.clear();
				if (request_user.isChanged())
					request_user.fire(new Receiver<Void>() {
						@Override
						public void onSuccess(Void arg0) {
							if (errorFlag)
								saveStatusLabel.setHTML("<p>Datenbankfehler: " + errorString + "</p>");
							else {
								saveStatusLabel.setHTML("<p>Speichern erfolgreich!</p>");
								save.removeFromParent();
							}
							save_dbox.center();
						}

						@Override
						public void onFailure(ServerFailure error) {
							saveStatusLabel.setHTML("<p>Serverfehler: " + error.getMessage() + "</p>");
							save_dbox.center();
						}
					});

				// Push the changes to the views.
				dataProvider.refresh();
			}
		});

		add.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				initAddDialog();
				add_dbox.center();
			}
		});

		delete.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				deleteStatusLabel.setText("");
				if (dataGrid.getColumnCount() < 5) {
					setDeleteColumn(true);
					delete.setText("Auswahl löschen");
				} else {
					delete_dbox.center();
				}
			}
		});

		page.add(userLoadLabel);
		page.add(dataGrid);
		page.add(pager);
		page.add(add);
		page.add(delete);
	}

	/**
	 * Add the columns to the table.
	 */
	private void initTableColumns(ListHandler<UserProxy> sortHandler) {
		// user id column
		Column<UserProxy, String> idColumn = new Column<UserProxy, String>(new TextCell()) {
			@Override
			public String getValue(UserProxy object) {
				return object.getId() + "";
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
		dataGrid.setColumnWidth(idColumn, 17, Unit.PCT);

		// firstname column
		Column<UserProxy, String> firstNameColumn = new Column<UserProxy, String>(new EditTextCell()) {
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
				if (!save.isAttached())
					page.add(save);
				pendingChanges.add(new FirstNameChange(object, value));

			};
		});
		dataGrid.setColumnWidth(firstNameColumn, 33, Unit.PCT);

		// lastname column
		Column<UserProxy, String> lastNameColumn = new Column<UserProxy, String>(new EditTextCell()) {
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
				if (!save.isAttached())
					page.add(save);
				pendingChanges.add(new LastNameChange(object, value));
			}
		});
		dataGrid.setColumnWidth(lastNameColumn, 33, Unit.PCT);

		// nickname column
		Column<UserProxy, String> nickNameColumn = new Column<UserProxy, String>(new EditTextCell()) {
			@Override
			public String getValue(UserProxy object) {
				String nickname = object.getNickname();
				if (nickname != null)
					return nickname;
				else
					return "";
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
				if (!save.isAttached())
					page.add(save);
				pendingChanges.add(new NickNameChange(object, value));
			}
		});
		dataGrid.setColumnWidth(nickNameColumn, 33, Unit.PCT);
	}

	private void setDeleteColumn(boolean show) {
		// Checkbox column. This table will uses a checkbox column for selection.
		// Alternatively, you can call dataGrid.setSelectionEnabled(true) to enable mouse selection.
		if (show) {
			Column<UserProxy, Boolean> checkColumn = new Column<UserProxy, Boolean>(new CheckboxCell(true, false)) {
				@Override
				public Boolean getValue(UserProxy object) {
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

		request_user.findAllUsers().fire(new Receiver<List<UserProxy>>() {
			public void onSuccess(List<UserProxy> cards) {
				dataProvider.getList().clear();
				dataProvider.getList().addAll(cards);
				userLoadLabel.setHTML("<p>Status: Card Data loaded</p>");
			}

			@Override
			public void onFailure(ServerFailure error) {
				userLoadLabel.setHTML("<p>" + error.getMessage() + "</p>");
			}
		});
	}

	private void addUser(String firstname, String lastname, String nickname) {
		requestFactory.userRequest().createUser(firstname, lastname, nickname).fire(new Receiver<UserProxy>() {
			@Override
			public void onSuccess(UserProxy user) {
				dataProvider.getList().add(user);
				add_dbox.hide();
			}

			@Override
			public void onFailure(ServerFailure error) {
				addStatusLabel.setText("Fehler: " + error.getMessage());
			}
		});
	}

	private void deleteUser() {
		UserRequest user_req = requestFactory.userRequest();
		for (final UserProxy user : dataProvider.getList()) {
			if (selectionModel.isSelected(user)) {
				user_req.delete(user).to(new Receiver<Void>() {
					@Override
					public void onSuccess(Void arg0) {
						dataProvider.getList().remove(user);
					}

					@Override
					public void onFailure(ServerFailure error) {
						errorFlagDelete = true;
						errorStringDelete = error.getMessage();
					}
				});
			}
		}
		user_req.fire(new Receiver<Void>() {
			@Override
			public void onSuccess(Void arg0) {
				if (errorFlagDelete)
					deleteStatusLabel.setHTML("<p>Datenbankfehler: " + errorStringDelete + "</p>");
				else {
					delete.setText("Löschmodus");
					setDeleteColumn(false);
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

	private void initAddDialog() {
		final FlowPanel addpanel = new FlowPanel();
		final Button add_ok = new Button("Hinzufügen");
		final Button add_cancel = new Button("Abbrechen");
		final TextBox firstname_box = new TextBox();
		final TextBox lastname_box = new TextBox();
		final TextBox nickname_box = new TextBox();
		final HTML firstname_text = new HTML("<p>Vorname: </p>");
		final HTML lastname_text = new HTML("<p>Nachname: </p>");
		final HTML nickname_text = new HTML("<p>Spitzname: </p>");
		addStatusLabel.setText("");
		add_ok.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (addStatusLabel.getText().equals("")) {
					addUser(firstname_box.getText(), lastname_box.getText(), nickname_box.getText());
				}
			}
		});
		add_cancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				add_dbox.hide();
			}
		});
		addpanel.clear();
		add_dbox.clear();
		addpanel.add(firstname_text);
		addpanel.add(firstname_box);
		addpanel.add(lastname_text);
		addpanel.add(lastname_box);
		addpanel.add(nickname_text);
		addpanel.add(nickname_box);
		addpanel.add(addStatusLabel);
		addpanel.add(add_ok);
		addpanel.add(add_cancel);

		// addpanel.setSize("200px", "80px");
		add_dbox.setAutoHideOnHistoryEventsEnabled(true);
		add_dbox.setText("Benutzer hinzufügen ...");
		add_dbox.setWidget(addpanel);
	}

	private void initDeleteDialog() {
		final FlowPanel delpanel = new FlowPanel();
		final Button add_ok = new Button("Ja");
		final Button add_cancel = new Button("Nein");
		final HTML question = new HTML("<p>Wirklich löschen? </p>");
		deleteStatusLabel.setText("");
		add_ok.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				deleteUser();
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

		// addpanel.setSize("200px", "80px");
		delete_dbox.setAutoHideOnHistoryEventsEnabled(true);
		delete_dbox.setText("Benutzer löschen");
		delete_dbox.setWidget(delpanel);
	}
}
