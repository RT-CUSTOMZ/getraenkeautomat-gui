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

import de.rtcustomz.getraenkeautomat.client.proxies.SlotProxy;
import de.rtcustomz.getraenkeautomat.shared.ModelRequestFactory;
import de.rtcustomz.getraenkeautomat.shared.requests.SlotRequest;

public class AdminSlotPage extends Page {

    static private AdminSlotPage _instance = null;
    private static final String pageName = "Getränke";

    private final ModelRequestFactory requestFactory = GWT.create(ModelRequestFactory.class);
    SlotRequest request_slot = requestFactory.slotRequest();
    final EventBus eventBus = new SimpleEventBus();

    DataGrid<SlotProxy> dataGrid;
    SimplePager pager;

    private final ListDataProvider<SlotProxy> dataProvider = new ListDataProvider<SlotProxy>();

    final HTML slotLoadLabel = new HTML();

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
	dataGrid.setHeight("300px");

	dataGrid.setEmptyTableWidget(new Label("Leere Tabelle..."));
	final ListHandler<SlotProxy> sortHandler = new ListHandler<SlotProxy>(dataProvider.getList());
	dataGrid.addColumnSortHandler(sortHandler);

	SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	pager.setDisplay(dataGrid);

	final SelectionModel<SlotProxy> selectionModel = new MultiSelectionModel<SlotProxy>();
	dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager.<SlotProxy> createCheckboxManager());

	initTableColumns(selectionModel, sortHandler);

	showGrid();

	page.add(slotLoadLabel);
	page.add(dataGrid);
	page.add(pager);
    }

    /**
     * Add the columns to the table.
     */
    private void initTableColumns(final SelectionModel<SlotProxy> selectionModel, ListHandler<SlotProxy> sortHandler) {
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
		request_slot = requestFactory.slotRequest();
		SlotProxy slot = request_slot.edit(object);
		slot.setDrink(value);
		request_slot.save(slot).fire(new Receiver<Void>() {
		    @Override
		    public void onSuccess(Void arg0) {
			dataProvider.refresh();
		    }

		});
	    }
	});
	dataGrid.setColumnWidth(drinkColumn, 20, Unit.PCT);
    }

    private void showGrid() {
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
