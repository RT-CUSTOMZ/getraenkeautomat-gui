package de.rtcustomz.getraenkeautomat.client.charts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
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
import com.googlecode.gwt.charts.client.controls.filter.NumberRangeFilterState;
import com.googlecode.gwt.charts.client.corechart.ColumnChartOptions;
import com.googlecode.gwt.charts.client.event.StateChangeEvent;
import com.googlecode.gwt.charts.client.event.StateChangeHandler;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;

import de.rtcustomz.getraenkeautomat.client.proxies.ColumnChartDataProxy;
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
    private List<ColumnChartDataProxy> history;
    private List<SlotProxy> slots;

    private int year = Integer.parseInt( DateTimeFormat.getFormat("yyyy").format(new Date()) );
    private int month = 5;//Integer.parseInt( DateTimeFormat.getFormat("MM").format(new Date()) ); // = 5;
    private int lastDayOfMonth;
	
	public ColumnChartPage()
	{
		requestFactory.initialize(eventBus);
		
		getSlots();
		getHistory();
//        initPage();
		
		setLastDayOfMonth();

        // draw new PieChart if user resizes the browser window
        Window.addResizeHandler(this);
        
        initWidget(page);
	}
	
    private void setLastDayOfMonth() {
    	// TODO: use library that can handle Dates much better
		lastDayOfMonth = new Date(year-1900, month, 0).getDate();
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
//		page.add( getColumnChart() );
		
//		drawChart();
	}
	
	private void getHistory() {
		HistoryRequest historyrequest = requestFactory.historyRequest();
		historyrequest.getColumnChartData(year).fire(new Receiver<List<ColumnChartDataProxy>>() {

			@Override
			public void onSuccess(List<ColumnChartDataProxy> response) {
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
		// chart can only been drawn if history and slots have been loaded
		if(history == null || slots == null)
			return;
		
		// Prepare the data
		DataTable dataTable = DataTable.create();
		
		dataTable.addColumn(ColumnType.NUMBER, "Monat");
		for(int i=0; i<slots.size(); i++) {
			dataTable.addColumn(ColumnType.NUMBER, slots.get(i).getDrink());
		}
		
		dataTable.addRows(lastDayOfMonth);
		
		addChartData(dataTable);
		
		// Set control options
		NumberRangeFilterOptions numberRangeFilterOptions = NumberRangeFilterOptions.create();
		numberRangeFilterOptions.setFilterColumnLabel("Monat");
		numberRangeFilterOptions.setMinValue(1);
		numberRangeFilterOptions.setMaxValue(lastDayOfMonth);
		numberRangeFilter.setOptions(numberRangeFilterOptions);
		
		ColumnChartOptions options = ColumnChartOptions.create();
		final HAxis hAxis = HAxis.create("Monat");
		
		int[] ticks = new int[lastDayOfMonth];
		for(int day=0; day<lastDayOfMonth; day++) {
			ticks[day] = day+1;
		}
		
		hAxis.setTicks(ticks);
		
		numberRangeFilter.addStateChangeHandler(new StateChangeHandler() {
			
			@Override
			public void onStateChange(StateChangeEvent event) {
				NumberRangeFilterState state = numberRangeFilter.getState().cast();
				int low = (int)state.getLowValue();
				int high = (int)state.getHighValue();
				
				int newTicks[] = new int[high-low];
				for(int i=0;i < high-low; i++) {
					newTicks[i] = low+i;
				}
				
				hAxis.setTicks(newTicks);
			}
		});
		
		options.setTitle("Getränke entnommen im Monat " + month + "." + year + ":");
		options.setHAxis(hAxis);
		options.setVAxis(VAxis.create("entnommen"));
		columnChart.setOptions(options);
		
//		columnChart.draw(dataTable, options);
		
		dashboard.bind(numberRangeFilter, columnChart);
		dashboard.draw(dataTable);
	}
	
	private void addChartData(DataTable dataTable) {
		for(int day=0, j=0; day<lastDayOfMonth; day++) {
			dataTable.setValue(day, 0, day+1);
			
			List<String> drinksInHistory = new ArrayList<>();
			
			while( j<history.size() ) {
				ColumnChartDataProxy data = history.get(j);
				
				if( data.getTimeSpan() != (day+1)) {
					break;
				}
					
				final Integer count = data.getCount();
				final String drink = data.getDrink();
				
				if(!drinksInHistory.contains(drink))
					drinksInHistory.add(drink);
				
				for(int col = 0; col < slots.size(); col++) {
					if(slots.get(col).getDrink() == drink) {
						dataTable.setValue(day, col+1, count);
					}
				}
				j++;
			}
			
			if(drinksInHistory.size() != slots.size()) {
				for(int col = 0; col < slots.size(); col++) {
					final String drink = slots.get(col).getDrink();
					
					if( !drinksInHistory.contains(drink) ) {
						dataTable.setValue(day, col+1, 0);
					}
				}
			}
		}
	}

	@Override
	public void onResize(ResizeEvent event) {
		redrawChart();
	}

	@Override
	public void redrawChart() {
		if(history == null || slots == null || dashboard == null)
			return;
		
		// remove possible errors
		dashboard.getElement().setInnerHTML("");
		dashboard.redraw();
	}

}
