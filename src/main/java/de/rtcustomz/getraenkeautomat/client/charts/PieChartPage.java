package de.rtcustomz.getraenkeautomat.client.charts;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
    
    private int year = Integer.parseInt( DateTimeFormat.getFormat("yyyy").format(new Date()) );
    private int month = 5;//Integer.parseInt( DateTimeFormat.getFormat("MM").format(new Date()) ); // = 5;
	private Integer slotsCount;
    
    private boolean diffMode = true;
	
	public PieChartPage()
	{
		requestFactory.initialize(eventBus);

		getHistory();
		
		getSlotsCount();
		
//        initPage();
		
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
		
//		drawChart();
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
				//history = response;
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
				//history = response;
				if(pieChart != null) {
					drawChart();
				}
			}
			
		});
	}
	
	private void getSlotsCount() {
		SlotRequest slotrequest = requestFactory.slotRequest();
    	slotrequest.countSlots().fire(new Receiver<Long>() {

			@Override
			public void onSuccess(Long response) {
				slotsCount = response.intValue();
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
		// chart can only been drawn if history has been loaded
		if(historySelectedMonth == null || historyMonthBefore == null || slotsCount == null)
			return;
		
		// Prepare the data
		DataTable newData = DataTable.create();
		addRows(newData, historySelectedMonth.entrySet());
		
		PieChartOptions options = PieChartOptions.create();
		
		DataSource drawData;
		String title;
		
		if(diffMode) {
			DataTable oldData = DataTable.create();
			addRows(oldData, historyMonthBefore.entrySet());
			
			// TODO: der mag's nicht, wenn keine aktuellen Daten vorliegen
			title = "Getränke entnommen im Monat " + month + "." + year + " im Vergleich zum Vormonat:";
			drawData = pieChart.computeDiff(oldData, newData);
		} else {
			title = "Getränke entnommen im Monat " + month + "." + year + ":";
			
			drawData = newData;
		}
		
		options.setTitle(title);
		
		pieChart.draw(drawData, options);
	}

	private void addRows(DataTable dataTable, Set<Entry<String, Long>> entrySet) {
		dataTable.addColumn(ColumnType.STRING, "Getränk");
		dataTable.addColumn(ColumnType.NUMBER, "entnommen");
		
		dataTable.addRows(slotsCount);
		
		int i = 0;
		
		for (Entry<String, Long> entry : entrySet) {
			String drink = entry.getKey();
			Long count = entry.getValue();
			
			dataTable.setValue(i, 0, drink);	// set drink name
			dataTable.setValue(i, 1, count);	// set count of slots in history
			
			i++;
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

}
