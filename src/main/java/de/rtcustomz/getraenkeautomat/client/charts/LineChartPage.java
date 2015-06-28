package de.rtcustomz.getraenkeautomat.client.charts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.googlecode.gwt.charts.client.corechart.LineChartOptions;
import com.googlecode.gwt.charts.client.event.StateChangeEvent;
import com.googlecode.gwt.charts.client.event.StateChangeHandler;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;

import de.rtcustomz.getraenkeautomat.client.proxies.LineChartDataProxy;
import de.rtcustomz.getraenkeautomat.client.proxies.SlotProxy;
import de.rtcustomz.getraenkeautomat.shared.ModelRequestFactory;
import de.rtcustomz.getraenkeautomat.shared.requests.HistoryRequest;
import de.rtcustomz.getraenkeautomat.shared.requests.SlotRequest;

public class LineChartPage extends ChartPage {
	
	static private LineChartPage _instance = null;
	private static final String pageName = "Line Chart";
	private static enum Mode { MONTH, DAY };

	private final ModelRequestFactory requestFactory = GWT.create(ModelRequestFactory.class);
	private final EventBus eventBus = new SimpleEventBus();
	
	private Dashboard dashboard;
	private NumberRangeFilter numberRangeFilter;
	private ChartWrapper<LineChartOptions> lineChart;
    private List<LineChartDataProxy> history;
    private List<SlotProxy> slots;
    

    private int year = Integer.parseInt( DateTimeFormat.getFormat("yyyy").format(new Date()) );
    private int month = Integer.parseInt( DateTimeFormat.getFormat("MM").format(new Date()) );
    private int day = Integer.parseInt( DateTimeFormat.getFormat("d").format(new Date()) );
    private int toValue;
    private int fromValue;
    private Mode currentMode = Mode.MONTH;
    
    // TODO: set days and months as x-axis labels of chart
//  private String[] days = {"Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"};
//  private String[] months = {"Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"};
	
	public LineChartPage()
	{
		requestFactory.initialize(eventBus);
		
        // draw new PieChart if user resizes the browser window
        Window.addResizeHandler(this);
        
        initWidget(page);
	}

	// TODO: use library that can handle Dates much better
    private int getLastDayOfMonth() {
		return new Date(year-1900, month, 0).getDate();
	}
    
    private void setFirstAndLastEntry() {
    	switch(currentMode) {
    		case MONTH:
    			fromValue = 1;
    			toValue = getLastDayOfMonth();
    			break;
    			
    		case DAY:
    			fromValue = 0;
    			toValue = 23;
    			break;
    	}
	}

	public static LineChartPage getInstance(){
        if(null == _instance) {
            _instance = new LineChartPage();
        }
        return _instance;
    }
    
    public static String getPageName()
    {
    	return pageName;
    }

	@Override
	public void initPage() {
		page.add( getLineChart() );
		page.add( getDashboard() );
		page.add( getNumberRangeFilter() );
	}
	
	private void getHistory() {
		HistoryRequest historyrequest = requestFactory.historyRequest();
		Receiver<List<LineChartDataProxy>> historyReceiver = new Receiver<List<LineChartDataProxy>>() {

			@Override
			public void onSuccess(List<LineChartDataProxy> response) {
				history = response;
				
				setFirstAndLastEntry();
				
				if(dashboard != null) {
					drawChart();
				}
			}
			
		};
		
		switch(currentMode) {
			case MONTH:
				historyrequest.getLineChartData(month, year).fire(historyReceiver);
				break;
				
			case DAY:
				historyrequest.getLineChartData(day, month, year).fire(historyReceiver);
				break;
		}
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
	
	private Widget getDashboard() {
		if (dashboard == null) {
			dashboard = new Dashboard();
			dashboard.addStyleName("dashboard");
		}
		return dashboard;
	}
	
	private ChartWrapper<LineChartOptions> getLineChart() {
		if (lineChart == null) {
			lineChart = new ChartWrapper<LineChartOptions>();
			lineChart.setChartType(ChartType.LINE);
			lineChart.setStyleName("chart");
		}
		return lineChart;
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
		
		int rowCount = toValue-fromValue + 1;
		String xAxisLabel, title;
		
		// Prepare the data
		DataTable dataTable = DataTable.create();
		
		switch(currentMode) {
			case DAY:
				xAxisLabel = "Uhrzeit";
				title = "Getränke entnommen am Tag " + day + "." + month + "." + year + ":";
				break;
			
			// default of mode is day
			case MONTH:
			default:
				xAxisLabel = "Tag";
				title = "Getränke entnommen im Monat " + month + "." + year + ":";
		}
		
		dataTable.addColumn(ColumnType.NUMBER, xAxisLabel);
		for(int i=0; i<slots.size(); i++) {
			dataTable.addColumn(ColumnType.NUMBER, slots.get(i).getDrink());
		}
		
		dataTable.addRows(rowCount);
		
		addChartData(dataTable);
		
		// Set control options
		NumberRangeFilterOptions numberRangeFilterOptions = NumberRangeFilterOptions.create();
		numberRangeFilterOptions.setFilterColumnLabel(xAxisLabel);
		numberRangeFilterOptions.setMinValue(fromValue);
		numberRangeFilterOptions.setMaxValue(toValue);
		numberRangeFilter.setOptions(numberRangeFilterOptions);
		
		LineChartOptions options = LineChartOptions.create();
		final HAxis hAxis = HAxis.create(xAxisLabel);
		
		int[] ticks = new int[rowCount];
		for(int i=0; i<rowCount; i++) {
			ticks[i] = fromValue + i;
		}
		
		hAxis.setTicks(ticks);
		
		numberRangeFilter.addStateChangeHandler(new StateChangeHandler() {
			
			@Override
			public void onStateChange(StateChangeEvent event) {
				NumberRangeFilterState state = numberRangeFilter.getState().cast();
				int low = (int)state.getLowValue();
				int high = (int)state.getHighValue();
				
				int newTicks[] = new int[high-low];
				for(int i=0;i <= high-low; i++) {
					newTicks[i] = low+i;
				}
				
				hAxis.setTicks(newTicks);
			}
		});
		
		options.setTitle(title);
		options.setHAxis(hAxis);
		options.setVAxis(VAxis.create("entnommen"));
		lineChart.setOptions(options);
		
		dashboard.bind(numberRangeFilter, lineChart);
		dashboard.draw(dataTable);
	}

	private void addChartData(DataTable dataTable) {
		int rowCount = toValue-fromValue + 1;
		
		for(int i=0, j=0; i<rowCount; i++) {
			
			int time = fromValue + i;
			
			dataTable.setValue(i, 0, time);
			
			List<String> drinksInHistory = new ArrayList<>();
			
			while( j<history.size() ) {
				LineChartDataProxy data = history.get(j);
				
				if( data.getTimeSpan() != time) {
					break;
				}
					
				final Integer count = data.getCount();
				final String drink = data.getDrink();
				
				if(!drinksInHistory.contains(drink))
					drinksInHistory.add(drink);
				
				for(int col = 0; col < slots.size(); col++) {
					if(slots.get(col).getDrink() == drink) {
						dataTable.setValue(i, col+1, count);
					}
				}
				j++;
			}
			
			if(drinksInHistory.size() != slots.size()) {
				for(int col = 0; col < slots.size(); col++) {
					final String drink = slots.get(col).getDrink();
					
					if( !drinksInHistory.contains(drink) ) {
						dataTable.setValue(i, col+1, 0);
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

	@Override
	public void setMode(String mode) {
		if(mode.equals("month")) {
			currentMode = Mode.MONTH;
		} else if(mode.equals("day")) {
			currentMode = Mode.DAY;
		}
	}

	@Override
	public void setFilter(Map<String, String> filter) {
		if(filter.containsKey("year")) {
			int year = Integer.parseInt( filter.get("year") );
			if(year >= 2000 && year <= 2100)
				this.year = year;
		}
		
		if(filter.containsKey("month")) {
			int month = Integer.parseInt( filter.get("month") );
			if(month >=1 && month <= 12)
				this.month = month;
		}
		
		if(filter.containsKey("day")) {
			int day = Integer.parseInt( filter.get("day") );
			if(day >=0 && day <= 31)
				this.day = day;
		}
	}

	@Override
	public void initData() {
		getSlots();
		getHistory();
	}

}
