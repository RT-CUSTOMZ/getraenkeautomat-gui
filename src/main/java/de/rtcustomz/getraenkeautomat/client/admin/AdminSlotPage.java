package de.rtcustomz.getraenkeautomat.client.admin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
import com.google.gwt.view.client.ListDataProvider;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

import de.rtcustomz.getraenkeautomat.client.proxies.SlotProxy;
import de.rtcustomz.getraenkeautomat.shared.ModelRequestFactory;
import de.rtcustomz.getraenkeautomat.shared.requests.SlotRequest;

public class AdminSlotPage extends AdminPage {

	/**
	 * A pending change to a {@link SlotProxy}. Changes aren't committed
	 * immediately to illustrate that cells can remember their pending changes.
	 * 
	 * @param <T>
	 *            the data type being changed
	 */
	private abstract static class PendingChange<T> {
		private final SlotProxy slot;
		private final T value;

		public PendingChange(SlotProxy slot, T value) {
			this.slot = slot;
			this.value = value;
		}

		/**
		 * Commit the change to the contact.
		 */
		public void commit() {
			doCommit(slot, value);
		}

		/**
		 * Update the appropriate field in the {@link ContactInfo}.
		 * 
		 * @param slot
		 *            the contact to update
		 * @param value
		 *            the new value
		 */
		protected abstract void doCommit(SlotProxy slot, T value);
	}

	/**
	 * Updates the drink name.
	 */
	private static class DrinkChange extends PendingChange<String> {

		public DrinkChange(SlotProxy slot, String value) {
			super(slot, value);
		}

		@Override
		protected void doCommit(SlotProxy slot, String value) {
			request_slot = requestFactory.slotRequest();
			SlotProxy newslot = request_slot.edit(slot);
			newslot.setDrink(value);
			request_slot.save(newslot).to(new Receiver<Void>() {
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

	static private AdminSlotPage _instance = null;
	private static final String pageName = "Getränke";

	private final static ModelRequestFactory requestFactory = GWT.create(ModelRequestFactory.class);
	static SlotRequest request_slot = requestFactory.slotRequest();
	// static RequestContext context = requestFactory.slotRequest();
	final EventBus eventBus = new SimpleEventBus();

	DataGrid<SlotProxy> dataGrid;
	SimplePager pager;

	private final static ListDataProvider<SlotProxy> dataProvider = new ListDataProvider<SlotProxy>();
	private List<PendingChange<?>> pendingChanges = new ArrayList<PendingChange<?>>();

	final HTML slotLoadLabel = new HTML();
	final Button save = new Button("Speichern");
	final static DialogBox dbox = new DialogBox(true);
	final static HTML saveStatusLabel = new HTML();

	static boolean errorFlag = false;
	static String errorString;

	public AdminSlotPage() {
		initPage();
		initWidget(page);
	}

	public static AdminSlotPage getInstance() {
		if (null == _instance) {
			_instance = new AdminSlotPage();
		}
		return _instance;
	}

	public static String getPageName() {
		return pageName;
	}

	@Override
	public void initPage() {
		requestFactory.initialize(eventBus);
		dataGrid = new DataGrid<SlotProxy>();
		dataGrid.setWidth("100%");
		dataGrid.setHeight("200px");

		dataGrid.setEmptyTableWidget(new Label("Leere Tabelle..."));
		final ListHandler<SlotProxy> sortHandler = new ListHandler<SlotProxy>(dataProvider.getList());
		dataGrid.addColumnSortHandler(sortHandler);

		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(dataGrid);

		final FlowPanel dialogpanel = new FlowPanel();
		final Button ok = new Button("OK");
		ok.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dbox.hide();
			}
		});
		dialogpanel.add(saveStatusLabel);
		dialogpanel.add(ok);
		dialogpanel.setSize("200px", "80px");
		dbox.setAutoHideOnHistoryEventsEnabled(true);
		dbox.setText("Daten Speichern ...");
		dbox.setWidget(dialogpanel);

		initTableColumns(sortHandler);

		showGrid();

		save.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Commit the changes
				request_slot = requestFactory.slotRequest();
				for (PendingChange<?> pendingChange : pendingChanges) {
					pendingChange.commit();
				}
				pendingChanges.clear();
				if (request_slot.isChanged())
					request_slot.fire(new Receiver<Void>() {
						@Override
						public void onSuccess(Void arg0) {
							if (errorFlag)
								saveStatusLabel.setHTML("<p>Datenbankfehler: " + errorString + "</p>");
							else {
								saveStatusLabel.setHTML("<p>Speichern erfolgreich!</p>");
								save.removeFromParent();
							}
							dbox.center();
						}

						@Override
						public void onFailure(ServerFailure error) {
							saveStatusLabel.setHTML("<p>Serverfehler: " + error.getMessage() + "</p>");
							dbox.center();
						}
					});

				// Push the changes to the views.
				dataProvider.refresh();
			}
		});

		page.add(slotLoadLabel);
		page.add(dataGrid);
		page.add(pager);
		// page.add(save);
	}

	/**
	 * Add the columns to the table.
	 */
	private void initTableColumns(ListHandler<SlotProxy> sortHandler) {
		// slot id column
		Column<SlotProxy, String> idColumn = new Column<SlotProxy, String>(new TextCell()) {
			@Override
			public String getValue(SlotProxy object) {
				return object.getId() + "";
			}
		};
		idColumn.setSortable(true);
		sortHandler.setComparator(idColumn, new Comparator<SlotProxy>() {
			@Override
			public int compare(SlotProxy o1, SlotProxy o2) {
				return Integer.compare(o1.getId(), o2.getId());
			}
		});
		dataGrid.addColumn(idColumn, "Schacht");
		dataGrid.setColumnWidth(idColumn, 5, Unit.PCT);

		// drink name column
		Column<SlotProxy, String> drinkColumn = new Column<SlotProxy, String>(new EditTextCell()) {
			@Override
			public String getValue(SlotProxy object) {
				return object.getDrink();
			}
		};
		drinkColumn.setSortable(true);
		sortHandler.setComparator(drinkColumn, new Comparator<SlotProxy>() {
			@Override
			public int compare(SlotProxy o1, SlotProxy o2) {
				return o1.getDrink().compareTo(o2.getDrink());
			}
		});
		dataGrid.addColumn(drinkColumn, "Getränk");
		drinkColumn.setFieldUpdater(new FieldUpdater<SlotProxy, String>() {
			@Override
			public void update(int index, SlotProxy object, String value) {
				// Called when the user changes the value.
				if (!save.isAttached())
					page.add(save);
				pendingChanges.add(new DrinkChange(object, value));
			}
		});
		dataGrid.setColumnWidth(drinkColumn, 20, Unit.PCT);
	}

	public void showGrid() {
		if (dataProvider.getDataDisplays().size() != 0)
			return;
		dataProvider.addDataDisplay(dataGrid);

		request_slot.findAllSlots().fire(new Receiver<List<SlotProxy>>() {
			public void onSuccess(List<SlotProxy> slots) {
				dataProvider.getList().clear();
				dataProvider.getList().addAll(slots);
				slotLoadLabel.setHTML("<p>Status: Slot Data loaded</p>");
			}

			@Override
			public void onFailure(ServerFailure error) {
				slotLoadLabel.setHTML("<p>" + error.getMessage() + "</p>");
			}
		});
	}
}
