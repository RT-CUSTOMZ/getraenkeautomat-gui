package de.rtcustomz.getraenkeautomat.client;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;

import de.rtcustomz.getraenkeautomat.client.charts.ChartPage;

public class MainLayout extends Composite {
	private FlowPanel content = new FlowPanel();
	private FlowPanel wrapper = new FlowPanel();
	
	private final LinkedHashMap<String, Page> pages;
	
	public MainLayout(final LinkedHashMap<String, Page> pages) {
		this.pages = pages;
		
		initPage();
		
		final Entry<String, Page> firstPage = pages.entrySet().iterator().next();
		
		if(firstPage.getValue() instanceof ChartPage) {
			initCharts();
		}
		
		initHistory(firstPage.getKey());
		// showPage(startPage);
		
		initWidget(wrapper);
	}
	
	native void console( Object message) /*-{
	    console.log( message );
	}-*/;
	
	private void initCharts() {
		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART, ChartPackage.CONTROLS);
        chartLoader.loadApi(new Runnable() {
            @Override
            public void run() {
//            	Iterator<Page> it = pages.values().iterator();
            	for(final Page page : pages.values()) {
            		final ChartPage chartPage = (ChartPage) page;
            		//showPage(chartPage);
            		chartPage.initPage();
            		chartPage.drawChart();
            	}
//            	while(it.hasNext()) {
//                	ChartPage page = (ChartPage)it.next();
//                	showPage(page);
//                	page.initPage();
//                	page.drawChart();
//            	}
            }
        });
	}

	private void initHistory(String firstPage) {
		String initToken = History.getToken();
	    if (initToken.length() == 0) {
	      History.newItem(firstPage);
	    }
		
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
			
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
            	final String pageName = event.getValue();
            	if(pages.containsKey(pageName)) {
            		Page page = pages.get(pageName);
                	showPage(page);
                	if(page instanceof ChartPage) {
                		((ChartPage)page).redrawChart();
                		//((ChartPage)page).onResize(null);
                	}
            	}
            	else showPage(ErrorPage.getInstance());
            }
		});

        History.fireCurrentHistoryState();
	}
	
	private void initPage() {
		MyResources.INSTANCE.css().ensureInjected();
		
		FlowPanel clear = new FlowPanel();
		clear.setStyleName("clear");

		Header header = new Header();
		Navigation nav = new Navigation(pages.keySet());
		Footer footer = new Footer();

		wrapper.getElement().setId("wrapper");
    	wrapper.add(header);
    	wrapper.add(nav);
    	wrapper.add(clear);
    	
    	content.getElement().setId("content");
    	
    	content.add(new HTMLPanel(HeadingElement.TAG_H1, "Inhalt wird geladen ..."));
    	
    	wrapper.add(content);
    	wrapper.add(footer);
	}
	
	private void showPage(Page page)
	{
		content.clear();
		content.add((Widget)page);
	}
}
