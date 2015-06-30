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
import com.google.gwt.user.client.ui.ListBox;
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
	private static enum Mode { OVERALL, YEAR, WEEK };

	private final ModelRequestFactory requestFactory = GWT.create(ModelRequestFactory.class);
	private final EventBus eventBus = new SimpleEventBus();
	
	private ListBox weekSelect = new ListBox();
	
	private Dashboard dashboard;
	private NumberRangeFilter numberRangeFilter;
	private ChartWrapper<ColumnChartOptions> columnChart;
    private List<ColumnChartDataProxy> history;
    private List<SlotProxy> slots;

    private int year;
    private int month;
    private int week = getWeek( new Date() );
    private int toValue;
    private int fromValue;
    private Mode currentMode = Mode.YEAR;
    
    // TODO: set days and months as x-axis labels of chart
//    private String[] days = {"Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"};
//    private String[] months = {"Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"};
	
	public ColumnChartPage()
	{
		requestFactory.initialize(eventBus);

        // draw new PieChart if user resizes the browser window
        Window.addResizeHandler(this);
        
        initWidget(page);
	}
	
	// TODO: use library that can handle Dates much better
    private int getWeek(Date date) {
    	Date yearStart = new Date(date.getYear(), 0, 0);

    	int week = (int) ( (date.getTime() - yearStart.getTime()) / (7 * 24 * 60 * 60 * 1000) );
		return week;
	}

	private void setFirstAndLastEntry() {
    	switch(currentMode) {
    		case OVERALL:
    			fromValue = history.get(0).getTimeSpan();
    			toValue = history.get(history.size()-1).getTimeSpan();
    			break;
    			
    		case YEAR:
    			fromValue = 1;
    			toValue = 12;
    			break;
    			
    		case WEEK:
    			fromValue = 1;
    			toValue = 7;
    			break;
    	}
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
	}
	
	private void getHistory() {
		HistoryRequest historyrequest = requestFactory.historyRequest();
		Receiver<List<ColumnChartDataProxy>> historyReceiver = new Receiver<List<ColumnChartDataProxy>>() {

			@Override
			public void onSuccess(List<ColumnChartDataProxy> response) {
				history = response;

				setFirstAndLastEntry();
				
				if(dashboard != null) {
					drawChart();
				}
			}
			
		};
		
		switch(currentMode) {
			case OVERALL:
				historyrequest.getColumnChartData().fire(historyReceiver);
				break;
				
			case YEAR:
				historyrequest.getColumnChartData(year).fire(historyReceiver);
				break;
				
			case WEEK:
				historyrequest.getColumnChartData(week, month, year).fire(historyReceiver);
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
	
	private ChartWrapper<ColumnChartOptions> getColumnChart() {
		if (columnChart == null) {
			columnChart = new ChartWrapper<ColumnChartOptions>();
			columnChart.setChartType(ChartType.COLUMN);
			columnChart.setStyleName("chart");
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
		
		int rowCount = toValue-fromValue + 1;
		String xAxisLabel, title;
		
		// Prepare the data
		DataTable dataTable = DataTable.create();
		
		switch(currentMode) {
			case OVERALL:
				xAxisLabel = "Jahr";
				title = "Getränke entnommen gesamt:";
				break;
				
			case WEEK:
				xAxisLabel = "Tag";
				title = "Getränke entnommen in KW " + week + " (" + month + ". Monat) des Jahres " + year + ":";
				break;
			
			// default of mode is year
			case YEAR:
			default:
				xAxisLabel = "Monat";
				title = "Getränke entnommen im Jahr " + year + ":";
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
		
		ColumnChartOptions options = ColumnChartOptions.create();
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
		columnChart.setOptions(options);
		
		dashboard.bind(numberRangeFilter, columnChart);
		dashboard.draw(dataTable);
	}
	
	private void addChartData(DataTable dataTable) {
		int rowCount = toValue-fromValue + 1;
		
		for(int i=0, j=0; i<rowCount; i++) {
			
			int time = fromValue + i;
			
			dataTable.setValue(i, 0, time);
			
			List<String> drinksInHistory = new ArrayList<>();
			
			while( j<history.size() ) {
				ColumnChartDataProxy data = history.get(j);
				
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
		if(mode == null) {
			currentMode = Mode.YEAR;
			return;
		}
			
		if(mode.equals("overall")) {
			currentMode = Mode.OVERALL;
		} else if(mode.equals("year")) {
			currentMode = Mode.YEAR;
		} else if(mode.equals("week")) {
			currentMode = Mode.WEEK;
		}
	}

	@Override
	public void setFilter(Map<String, String> filter) {
		try {
			Date now = new Date();
			
			if(filter.containsKey("year")) {
				int year = Integer.parseInt( filter.get("year") );
				if(year >= 2000 && year <= 2100)
					this.year = year;
			} else {
				this.year = Integer.parseInt( DateTimeFormat.getFormat("yyyy").format(now) );
			}
			
			if(filter.containsKey("month")) {
				int month = Integer.parseInt( filter.get("month") );
				if(month >=1 && month <= 12)
					this.month = month;
			} else {
				this.month = Integer.parseInt( DateTimeFormat.getFormat("MM").format(now) );
			}
			
			if(filter.containsKey("week")) {
				int week = Integer.parseInt( filter.get("week") );
				if(week >=1 && week <= 53)
					this.week = week;
			} else {
				this.week = getWeek( new Date() );
			}
		} catch(NumberFormatException e) {
			// TODO: show user that param is wrong
		}
	}

	@Override
	public void initData() {
		getSlots();
		getHistory();
	}

}
