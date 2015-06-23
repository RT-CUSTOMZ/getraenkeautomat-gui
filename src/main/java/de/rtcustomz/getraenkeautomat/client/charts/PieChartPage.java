package de.rtcustomz.getraenkeautomat.client.charts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.PieChart;
import com.googlecode.gwt.charts.client.corechart.PieChartOptions;

import de.rtcustomz.getraenkeautomat.client.proxies.HistoryEntryProxy;
import de.rtcustomz.getraenkeautomat.client.proxies.SlotProxy;
import de.rtcustomz.getraenkeautomat.shared.ModelRequestFactory;
import de.rtcustomz.getraenkeautomat.shared.requests.HistoryRequest;
import de.rtcustomz.getraenkeautomat.shared.requests.SlotRequest;

public class PieChartPage extends ChartPage {
	
	static private PieChartPage _instance = null;
	private static final String pageName = "Pie Chart";

	private final ModelRequestFactory requestFactory = GWT.create(ModelRequestFactory.class);
	private final EventBus eventBus = new SimpleEventBus();
	
    private PieChart pieChart;
    private List<HistoryEntryProxy> history;
    private List<SlotProxy> slots;
	
	public PieChartPage()
	{
		requestFactory.initialize(eventBus);
		
		getSlots();
		getHistory();
//        initPage();

        // draw new PieChart if user resizes the browser window
//        Window.addResizeHandler(new ResizeHandler() {
//			@Override
//			public void onResize(ResizeEvent event) {
//				if(pieChart != null) {
//					drawChart();
//				}
//			}
//        });
        
        initWidget(page);
	}
	
    public static PieChartPage getInstance(){
        if(null == _instance) {
            _instance = new PieChartPage();
        }
        return _instance;
    }
    
    public static String getPageName()
    {
    	return pageName;
    }

	@Override
	public void initPage() {
		page.add( getPieChart() );
		
		drawChart();
	}
	
	private void getHistory() {
		HistoryRequest historyrequest = requestFactory.historyRequest();
		historyrequest.findAllHistoryEntries().with("slot").fire(new Receiver<List<HistoryEntryProxy>>() {

			@Override
			public void onSuccess(List<HistoryEntryProxy> response) {
				history = response;
				if(pieChart != null) {
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
				slots = response;
				if(pieChart != null) {
					drawChart();
				}
			}
			
		});
	}
	
	private Widget getPieChart() {
		if (pieChart == null) {
	        pieChart = new PieChart();
	        pieChart.getElement().setId("chart");
		}
		return pieChart;
	}
	
	@Override
	public void drawChart() {
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
		
		// Set control options
//		NumberRangeFilterOptions numberRangeFilterOptions = NumberRangeFilterOptions.create();
//		numberRangeFilterOptions.setFilterColumnLabel("entnommen");
//		numberRangeFilterOptions.setMinValue(1);
//		numberRangeFilterOptions.setMaxValue(drinksObtained.size());
//		numberRangeFilter.setOptions(numberRangeFilterOptions);
		
		
		PieChartOptions options = PieChartOptions.create();
		options.setTitle("Getränke entnommen gesamt");
//		pieChart.setOptions(options);
		
//		pieChart.setWidth("100%");
//		pieChart.setHeight("100%");
		
		// Draw the chart
		pieChart.draw(dataTable, options);
		
//		dashboard.bind(numberRangeFilter, pieChart);
//		dashboard.draw(dataTable);
	}

}
