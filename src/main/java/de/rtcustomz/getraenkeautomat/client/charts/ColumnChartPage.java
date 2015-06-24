package de.rtcustomz.getraenkeautomat.client.charts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.googlecode.gwt.charts.client.ChartType;
import com.googlecode.gwt.charts.client.ChartWrapper;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.controls.Dashboard;
import com.googlecode.gwt.charts.client.controls.filter.NumberRangeFilter;
import com.googlecode.gwt.charts.client.controls.filter.NumberRangeFilterOptions;
import com.googlecode.gwt.charts.client.corechart.ColumnChartOptions;

import de.rtcustomz.getraenkeautomat.client.proxies.HistoryEntryProxy;
import de.rtcustomz.getraenkeautomat.client.proxies.SlotProxy;
import de.rtcustomz.getraenkeautomat.shared.ModelRequestFactory;
import de.rtcustomz.getraenkeautomat.shared.requests.HistoryRequest;
import de.rtcustomz.getraenkeautomat.shared.requests.SlotRequest;

public class ColumnChartPage extends ChartPage {
	
	static private ColumnChartPage _instance = null;
	private static final String pageName = "Column Chart";

	private final ModelRequestFactory requestFactory = GWT.create(ModelRequestFactory.class);
	private final EventBus eventBus = new SimpleEventBus();
	
	private Dashboard dashboard;
	private NumberRangeFilter numberRangeFilter;
	private ChartWrapper<ColumnChartOptions> columnChart;
//	private ColumnChart columnChart;
    private List<HistoryEntryProxy> history;
    private List<SlotProxy> slots;
	
	public ColumnChartPage()
	{
		requestFactory.initialize(eventBus);
		
		getSlots();
		getHistory();
//        initPage();

        // draw new PieChart if user resizes the browser window
        Window.addResizeHandler(this);
        
        initWidget(page);
	}
	
    public static ColumnChartPage getInstance(){
        if(null == _instance) {
            _instance = new ColumnChartPage();
        }
        return _instance;
    }
    
    public static String getPageName()
    {
    	return pageName;
    }

	@Override
	public void initPage() {
		page.add( getColumnChart() );
		page.add( getDashboard() );
		page.add( getNumberRangeFilter() );
		page.add( getColumnChart() );
		
//		drawChart();
	}
	
	private void getHistory() {
		HistoryRequest historyrequest = requestFactory.historyRequest();
		historyrequest.findAllHistoryEntries().with("slot").fire(new Receiver<List<HistoryEntryProxy>>() {

			@Override
			public void onSuccess(List<HistoryEntryProxy> response) {
				console("history loaded");
				history = response;
				if(dashboard != null) {
					drawChart();
				}
			}
			
		});
	}

	private void getSlots() {
		SlotRequest slotrequest = requestFactory.slotRequest();
    	slotrequest.findAllSlots().fire(new Receiver<List<SlotProxy>>() {

			@Override
			public void onSuccess(List<SlotProxy> response) {
				console("slots loaded");
				slots = response;
				if(dashboard != null) {
					drawChart();
				}
			}
			
		});
	}
	
//	private Widget getColumnChart() {
//		if (columnChart == null) {
//			columnChart = new ColumnChart();
//	        columnChart.getElement().setId("chart");
//		}
//		return columnChart;
//	}
	
	private Widget getDashboard() {
		if (dashboard == null) {
			dashboard = new Dashboard();
			dashboard.getElement().setId("dashboard");
		}
		return dashboard;
	}
	
	private ChartWrapper<ColumnChartOptions> getColumnChart() {
		if (columnChart == null) {
			columnChart = new ChartWrapper<ColumnChartOptions>();
			columnChart.setChartType(ChartType.COLUMN);
		}
		return columnChart;
	}
	
	private NumberRangeFilter getNumberRangeFilter() {
		if (numberRangeFilter == null) {
			numberRangeFilter = new NumberRangeFilter();
		}
		return numberRangeFilter;
	}
	
	@Override
	public void drawChart() {
		console("drawChart");
		// chart can only been drawn if history and slots have been loaded
		if(history == null || slots == null)
			return;
		HashMap<String, Integer> drinksObtained = new HashMap<>();
		
		// TODO: perhaps SQL statement for that?
		// count how much drinks has been obtained from every slot
		Iterator<HistoryEntryProxy> it = history.iterator();
		while(it.hasNext()) {
			HistoryEntryProxy historyEntry = it.next();
			
			String drink = historyEntry.getSlot().getDrink();
			
			Integer count = drinksObtained.get(drink);
            if (count == null)
                count = 0;
            count++;
            
            drinksObtained.put(drink, count);
		}
		console("dashboard vorher:");
		console(dashboard.getElement().toString());
		console("");
		
		// Prepare the data
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Getränk");
		dataTable.addColumn(ColumnType.NUMBER, "entnommen");
		
		// add so much entries in PieChart as slots exists
		dataTable.addRows(slots.size());
		for (int i = 0; i < slots.size(); i++) {
			String drink = slots.get(i).getDrink();
			Integer count = drinksObtained.get(drink);
			if(count == null)
				count = 0;
			dataTable.setValue(i, 0, drink);	// set drink name
			dataTable.setValue(i, 1, count);	// set count of slots in history
		}

		console("datatable:");
		console(dataTable.toString());
		console("");
		
		// Set control options
		NumberRangeFilterOptions numberRangeFilterOptions = NumberRangeFilterOptions.create();
		numberRangeFilterOptions.setFilterColumnLabel("entnommen");
		numberRangeFilterOptions.setMinValue(1);
		numberRangeFilterOptions.setMaxValue(drinksObtained.size());
		
		console("rangefilteroptions:");
		console(numberRangeFilterOptions.toString());
		console("");
		
		numberRangeFilter.setOptions(numberRangeFilterOptions);
		
		console("rangefilter after setoptions:");
		console(numberRangeFilter.toString());
		console("");
		
		
		ColumnChartOptions options = ColumnChartOptions.create();
		options.setTitle("Getränke entnommen gesamt");

		console("chartoptions:");
		console(options.toString());
		console("");
		
		columnChart.setOptions(options);

		console("chart after setoptions:");
		console(columnChart.toString());
		console("");
		
		
//		columnChart.draw(dataTable, options);
		
		dashboard.bind(numberRangeFilter, columnChart);
		
		console("dashboard after bind:");
		console(dashboard.toString());
		console("");
		
		dashboard.draw(dataTable);
		
		console("dashboard after draw:");
		console(dashboard.getElement().toString());
		console("");
	}

	@Override
	public void onResize(ResizeEvent event) {
//		if(dashboard != null) {
//			dashboard.redraw();
//		}
	}

	@Override
	public void redrawChart() {
		if(history == null || slots == null || dashboard == null)
			return;
		
		dashboard.getElement().setInnerHTML("");
		dashboard.redraw();
	}

}
