package de.rtcustomz.getraenkeautomat.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ChartType;
import com.googlecode.gwt.charts.client.ChartWrapper;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.controls.Dashboard;
import com.googlecode.gwt.charts.client.controls.filter.NumberRangeFilter;
import com.googlecode.gwt.charts.client.controls.filter.NumberRangeFilterOptions;
import com.googlecode.gwt.charts.client.corechart.PieChartOptions;

import de.rtcustomz.getraenkeautomat.client.proxies.HistoryEntryProxy;
import de.rtcustomz.getraenkeautomat.client.proxies.SlotProxy;
import de.rtcustomz.getraenkeautomat.shared.ModelRequestFactory;
import de.rtcustomz.getraenkeautomat.shared.requests.HistoryRequest;
import de.rtcustomz.getraenkeautomat.shared.requests.SlotRequest;

public class GetraenkeautomatGUI implements EntryPoint {
	// private final Messages messages = GWT.create(Messages.class);

	private final ModelRequestFactory requestFactory = GWT.create(ModelRequestFactory.class);
	private final EventBus eventBus = new SimpleEventBus();
	
//	private FlowPanel panel;
	private Dashboard dashboard;
//    private PieChart pieChart;
	private NumberRangeFilter numberRangeFilter;
	private ChartWrapper<PieChartOptions> pieChart;
    private List<HistoryEntryProxy> history;
    private List<SlotProxy> slots;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		requestFactory.initialize(eventBus);
		
		getSlots();
		getHistory();
		
		
		MyResources.INSTANCE.css().ensureInjected();
		
		FlowPanel wrapper = new FlowPanel();
		FlowPanel clear = new FlowPanel();
		clear.setStyleName("clear");

		Header header = new Header();
		Navigation nav = new Navigation("Projekte","Impressum");
		Footer footer = new Footer();

		wrapper.getElement().setId("wrapper");
    	wrapper.add(header);
    	wrapper.add(nav);
    	wrapper.add(clear);
    	
    	FlowPanel content = new FlowPanel();
    	content.getElement().setId("content");
    	
//    	content.add(getChartPanel());

    	wrapper.add(content);
    	wrapper.add(footer);
    	
    	RootPanel.get().add(wrapper);
    	
        // Create the API Loader
        ChartLoader chartLoader = new ChartLoader(ChartPackage.CONTROLS);
        chartLoader.loadApi(new Runnable() {
            @Override
            public void run() {
                //getChartPanel().add(getPieChart());
//            	Document.get().getElementById("content").appendChild( getPieChart().getElement() );
            	Document.get().getElementById("content").appendChild( getDashboard().getElement() );
            	Document.get().getElementById("content").appendChild( getNumberRangeFilter().getElement() );
            	Document.get().getElementById("content").appendChild( getPieChart().getElement() );
            	//RootPanel.get("content").add(getPieChart());
                drawDashboard();
            }
        });
        
        // draw new PieChart if user resizes the browser window
        Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				if(dashboard != null) {
					drawDashboard();
				}
			}
        });
	}
	
	native void console( Object message) /*-{
	    console.log(message);
	}-*/;
	
	private void getHistory() {
		HistoryRequest historyrequest = requestFactory.historyRequest();
		historyrequest.findAllHistoryEntries().with("slot").fire(new Receiver<List<HistoryEntryProxy>>() {

			@Override
			public void onSuccess(List<HistoryEntryProxy> response) {
				history = response;
				if(dashboard != null) {
					drawDashboard();
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
				if(dashboard != null) {
					drawDashboard();
				}
			}
			
		});
	}

//	private FlowPanel getChartPanel() {
//        if (panel == null) {
//            panel = new FlowPanel();
//            panel.getElement().setId("chart");
//        }
//        return panel;
//	}
	
	private Widget getDashboard() {
		if (dashboard == null) {
			dashboard = new Dashboard();
			dashboard.getElement().setId("dashboard");
		}
		return dashboard;
	}
	
	private ChartWrapper<PieChartOptions> getPieChart() {
		if (pieChart == null) {
			pieChart = new ChartWrapper<PieChartOptions>();
			pieChart.setChartType(ChartType.PIE);
		}
		return pieChart;
	}
	
	private NumberRangeFilter getNumberRangeFilter() {
		if (numberRangeFilter == null) {
			numberRangeFilter = new NumberRangeFilter();
		}
		return numberRangeFilter;
	}
	
//	private Widget getPieChart() {
//		if (pieChart == null) {
//	        pieChart = new PieChart();
//	        pieChart.getElement().setId("chart");
//		}
//		return pieChart;
//	}
	
	private void drawDashboard() {
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
		NumberRangeFilterOptions numberRangeFilterOptions = NumberRangeFilterOptions.create();
		numberRangeFilterOptions.setFilterColumnLabel("entnommen");
		numberRangeFilterOptions.setMinValue(1);
		numberRangeFilterOptions.setMaxValue(drinksObtained.size());
		numberRangeFilter.setOptions(numberRangeFilterOptions);
		
		
		PieChartOptions options = PieChartOptions.create();
		options.setTitle("Getränke entnommen gesamt");
		pieChart.setOptions(options);
		
//		pieChart.setWidth("100%");
//		pieChart.setHeight("100%");
		
		// Draw the chart
//		pieChart.draw(dataTable, options);
		
		dashboard.bind(numberRangeFilter, pieChart);
		dashboard.draw(dataTable);
	}

}
