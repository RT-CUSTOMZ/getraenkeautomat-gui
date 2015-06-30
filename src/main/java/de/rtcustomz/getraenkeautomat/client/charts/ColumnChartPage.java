package de.rtcustomz.getraenkeautomat.client.charts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
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
    private int week = calendarWeekIso( new Date() );//getWeek( new Date() );
    private int toValue;
    private int fromValue;
    private Mode currentMode = Mode.OVERALL;
    
    // TODO: set days and months as x-axis labels of chart
//    private String[] days = {"Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"};
    private String[] months = {"Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"};
	private int firstWeekInMonth = 0;
	
	public ColumnChartPage()
	{
		requestFactory.initialize(eventBus);
		
		FlowPanel selectBoxes = new FlowPanel();
		selectBoxes.setStyleName("selectboxes");
		
		modeSelect.addItem("Übersicht alle Jahre", "overall");
		modeSelect.addItem("Jahresansicht", "year");
		modeSelect.addItem("Wochenansicht", "week");
		
		for(int i=MINYEAR; i<=MAXYEAR; i++) {
			final String year = String.valueOf(i);
			yearSelect.addItem(year, year);
		}
		
		for(int i=MINMONTH; i<=MAXMONTH; i++) {
			final String month = String.valueOf(i);
			monthSelect.addItem(months[i-1], month);
		}
		
		modeSelect.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				final String mode = modeSelect.getSelectedValue();
				
				changeHistory("mode", mode);
				
				setMode(mode);
				
				initData();
				drawChart();
			}
		});
		
		yearSelect.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				final String year = yearSelect.getSelectedValue();
				
				changeHistory("year", year);
				
				setYear(year);
				
				initData();
				drawChart();
			}
		});
		
		monthSelect.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				final String month = monthSelect.getSelectedValue();
				
				changeHistory("month", month);
				
				setMonth(month);
				
				initData();
				drawChart();
			}
		});

		weekSelect.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				final String week = weekSelect.getSelectedValue();
				
				changeHistory("week", week);
				
				setWeek(week);
				
				initData();
				drawChart();
			}
		});
		weekSelect.setVisible(false);
		
		
		selectBoxes.add(modeSelect);
		selectBoxes.add(yearSelect);
		selectBoxes.add(monthSelect);
		selectBoxes.add(weekSelect);
		
		page.add(selectBoxes);

        // draw new PieChart if user resizes the browser window
        Window.addResizeHandler(this);
        
        initWidget(page);
	}
	
	// TODO: use library that can handle Dates much better
	private int calendarWeekIso(Date date) {
		Date thisThursday = new Date(date.getYear(), date.getMonth(), date.getDate() - weekday(date) + 4);
		Date firstOfJan = new Date(thisThursday.getYear(), 0, 1);
		Date firstThursdayOfYear = new Date(thisThursday.getYear(), 0, 1);
		
		while (weekday(firstThursdayOfYear) != 4) {
			firstThursdayOfYear.setDate(firstThursdayOfYear.getDate() + 1);
		}
		
		Date firstMondayOfYear = new Date(firstThursdayOfYear.getYear(), 0, firstThursdayOfYear.getDate() - 3);
		int cw = (int) ( (thisThursday.getTime() - firstMondayOfYear.getTime() ) / (7 * 24 * 60 * 60 * 1000) ) + 1;
		return cw;
	}
	
	private int weekday(Date date) {
		int weekday = date.getDay();
		if (weekday == 0) {
			weekday = 7;
		}
		return weekday;
	}
	
//    private int getWeek(Date date) {
//    	Date yearStart = new Date(date.getYear(), 0, 0);
//
//    	int week = (int) ( (date.getTime() - yearStart.getTime()) / (7 * 24 * 60 * 60 * 1000) );
//		return week;
//	}

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
			case YEAR:
				xAxisLabel = "Monat";
				title = "Getränke entnommen im Jahr " + year + ":";
				break;
				
			case WEEK:
				xAxisLabel = "Tag";
				title = "Getränke entnommen in KW " + week + " (" + month + ". Monat) des Jahres " + year + ":";
				break;
			
			// default of mode is year
			case OVERALL:
			default:
				xAxisLabel = "Jahr";
				title = "Getränke entnommen gesamt:";
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
			mode = new String("overall");
		}
			
		if(mode.equals("overall")) {
			currentMode = Mode.OVERALL;
			
			weekSelect.setVisible(false);
			monthSelect.setVisible(false);
			yearSelect.setVisible(false);
		} else if(mode.equals("year")) {
			currentMode = Mode.YEAR;

			weekSelect.setVisible(false);
			monthSelect.setVisible(false);
			yearSelect.setVisible(true);
		} else if(mode.equals("week")) {
			currentMode = Mode.WEEK;

			weekSelect.setVisible(true);
			monthSelect.setVisible(true);
			yearSelect.setVisible(true);
		}
		modeSelect.setSelectedIndex(currentMode.ordinal());
	}
	
	private void updateWeekSelect() {
		final int from = firstWeekInMonth;
		final int to = calendarWeekIso(new Date(year-1900, month, 0)); // getWeek
		
		// empty data
		weekSelect.getElement().setInnerHTML("");
		
		for(int i=0; i<=to-from; i++) {
			final String week = String.valueOf(from+i);
			weekSelect.insertItem(week, week, i);
		}
		weekSelect.setSelectedIndex(0);
	}

	public void setYear(String year) {
		if(year != null) {
			try {
				int y = Integer.parseInt( year );
				this.year = y;
			} catch (NumberFormatException e) {
				// TODO: show user info that parameter isn't correct
			}
		} else {
			this.year = Integer.parseInt( DateTimeFormat.getFormat("yyyy").format( new Date()) );
		}
		firstWeekInMonth = calendarWeekIso(new Date(this.year-1900, this.month-1, 1)); // getWeek
		setWeek( String.valueOf(firstWeekInMonth) );
		
		yearSelect.setSelectedIndex(this.year-MINYEAR);
	}
	
	public void setMonth(String month) {
		if(month != null) {
			try {
				int m = Integer.parseInt( month );
				if(m >=1 && m <= 12)
					this.month = m;
			} catch (NumberFormatException e) {
				// TODO: show user info that parameter isn't correct
			}
		} else {
			this.month = Integer.parseInt( DateTimeFormat.getFormat("MM").format( new Date()) );
		}
		firstWeekInMonth = calendarWeekIso(new Date(year-1900, this.month-1, 1)); // getWeek
		setWeek( String.valueOf(firstWeekInMonth) );
		
		monthSelect.setSelectedIndex(this.month-MINMONTH);
		updateWeekSelect();
	}
	
	public void setWeek(String week) {
		if(week != null) {
			try {
				int w = Integer.parseInt( week );
				if(w >=1 && w <= 53)
					this.week = w;
			} catch (NumberFormatException e) {
				// TODO: show user info that parameter isn't correct
			}
		} else {
			this.week = calendarWeekIso( new Date() ); // getWeek
			console("week: "+this.week);
		}
		weekSelect.setSelectedIndex(this.week-firstWeekInMonth);
	}

	@Override
	public void setFilter(Map<String, String> filter) {
		setYear(filter.get("year"));
		setMonth(filter.get("month"));
		setWeek(filter.get("week"));
	}

	@Override
	public void initData() {
		getSlots();
		getHistory();
	}

}
