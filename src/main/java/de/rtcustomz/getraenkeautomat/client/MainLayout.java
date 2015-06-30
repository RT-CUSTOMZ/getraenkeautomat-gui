package de.rtcustomz.getraenkeautomat.client;

import java.util.HashMap;
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
			initCharts(firstPage.getKey());
		} else {
			initHistory(firstPage.getKey());
		}
		
		initWidget(wrapper);
	}
	
	native void console( Object message) /*-{
	    console.log( message );
	}-*/;
	
	private void initCharts(final String startPage) {
		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART, ChartPackage.CONTROLS);
        chartLoader.loadApi(new Runnable() {
            @Override
            public void run() {
            	for(final Page page : pages.values()) {
            		page.initPage();
            	}
            	initHistory(startPage);
            }
        });
	}

	private void initHistory(String firstPage) {
		String initToken = History.getToken();
	    if (initToken.length() == 0) {
	      History.replaceItem("page="+firstPage);
	    }

        History.addValueChangeHandler(new ValueChangeHandler<String>() {
			
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
            	HashMap<String, String> tokens = new HashMap<>();
            	
            	// extract tokens from history
            	for(String token : event.getValue().split("&")) {
            		final String[] keyvalue = token.split("=");

            		tokens.put(keyvalue[0], keyvalue[1]);
            	}
            	
            	final String pageName = tokens.remove("page");
            	if(pages.containsKey(pageName)) {
            		Page startPage = pages.get(pageName);
            		
            		if(startPage instanceof ChartPage) {
            			ChartPage chartPage = (ChartPage)startPage;

        				chartPage.setMode(tokens.remove("mode"));
        				chartPage.setFilter(tokens);
            			
            			chartPage.initData();
                		chartPage.drawChart();
                	}
            		
                	showPage(startPage);
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
