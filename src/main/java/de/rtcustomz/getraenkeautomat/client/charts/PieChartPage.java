package de.rtcustomz.getraenkeautomat.client.charts;

import java.util.Date;
import java.util.HashMap;
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
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataSource;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.PieChart;
import com.googlecode.gwt.charts.client.corechart.PieChartOptions;

import de.rtcustomz.getraenkeautomat.client.proxies.PieChartDataProxy;
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
    private Map<String, Long> historySelectedMonth;
    private Map<String, Long> historyMonthBefore;
    private List<SlotProxy> slots;
    
    private int year = Integer.parseInt( DateTimeFormat.getFormat("yyyy").format(new Date()) );
    private int month = Integer.parseInt( DateTimeFormat.getFormat("MM").format(new Date()) );
    
    private boolean diffMode = false;
	
	public PieChartPage()
	{
		requestFactory.initialize(eventBus);
		
		Window.addResizeHandler(this);
        
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
	}
	
	private void getHistory() {
		HistoryRequest newhistory = requestFactory.historyRequest();
		newhistory.getPieChartData(month, year).fire(new Receiver<List<PieChartDataProxy>>() {

			@Override
			public void onSuccess(List<PieChartDataProxy> response) {
				historySelectedMonth = new HashMap<>();
				for(PieChartDataProxy row : response) {
					historySelectedMonth.put(row.getDrink(), row.getCount());
				}
				
				if(pieChart != null) {
					drawChart();
				}
			}
			
		});
		
		int yearOfPreviousMonth = year, previousMonth = month-1;
		
		if(month == 1) {
			yearOfPreviousMonth = year-1;
			previousMonth = 12;
		}
		
		HistoryRequest oldhistory = requestFactory.historyRequest();
		oldhistory.getPieChartData(previousMonth, yearOfPreviousMonth).fire(new Receiver<List<PieChartDataProxy>>() {

			@Override
			public void onSuccess(List<PieChartDataProxy> response) {
				historyMonthBefore = new HashMap<>();
				for(PieChartDataProxy row : response) {
					historyMonthBefore.put(row.getDrink(), row.getCount());
				}
				
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
	        pieChart.setStyleName("chart");
		}
		return pieChart;
	}
	
	@Override
	public void drawChart() {
		// chart can only been drawn if history has been loaded
		if(historySelectedMonth == null || historyMonthBefore == null || slots == null)
			return;
		
		// Prepare the data
		DataTable newData = DataTable.create();
		addRows(newData, historySelectedMonth);
		
		PieChartOptions options = PieChartOptions.create();
		
		DataSource drawData;
		String title;
		
		if(diffMode) {
			DataTable oldData = DataTable.create();
			addRows(oldData, historyMonthBefore);
			
			title = "Getränke entnommen im Monat " + month + "." + year + " im Vergleich zum Vormonat:";
			drawData = pieChart.computeDiff(oldData, newData);
		} else {
			title = "Getränke entnommen im Monat " + month + "." + year + ":";
			
			drawData = newData;
		}
		
		options.setTitle(title);
		
		pieChart.draw(drawData, options);
	}

	private void addRows(DataTable dataTable, Map<String, Long> history) {
		dataTable.addColumn(ColumnType.STRING, "Getränk");
		dataTable.addColumn(ColumnType.NUMBER, "entnommen");
		
		dataTable.addRows(slots.size());
		
		for(int col = 0; col < slots.size(); col++) {
			final String drink = slots.get(col).getDrink();
			double count = 0;
			
			if( history.containsKey(drink) ) {
				count = history.get(drink).doubleValue();
			}
			
			dataTable.setValue(col, 0, drink);	// set drink name
			dataTable.setValue(col, 1, count);	// set count of slots in history
		}
	}

	@Override
	public void onResize(ResizeEvent event) {
		redrawChart();
	}
	
	@Override
	public void redrawChart() {
		if(historySelectedMonth == null || pieChart == null)
			return;

		pieChart.redraw();
	}

	@Override
	public void setMode(String mode) {
		if(mode.equals("diff"))
			diffMode = true;
		else
			diffMode = false;
	}

	@Override
	public void setFilter(Map<String, String> filter) {
		try {
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
		} catch(NumberFormatException e) {
			// TODO: show user that param is wrong
		}
	}

	@Override
	public void initData() {
		getHistory();
		
		getSlots();
	}

}
