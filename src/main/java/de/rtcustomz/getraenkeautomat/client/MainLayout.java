package de.rtcustomz.getraenkeautomat.client;

import java.util.LinkedHashMap;

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

import de.rtcustomz.getraenkeautomat.client.admin.AdminPage;
import de.rtcustomz.getraenkeautomat.client.charts.ChartPage;

public class MainLayout extends Composite {
	private FlowPanel content = new FlowPanel();
	private FlowPanel wrapper = new FlowPanel();
	
	private final LinkedHashMap<String, Page> pages;
	
	public MainLayout(final LinkedHashMap<String, Page> pages) {
		this.pages = pages;
		final Page startPage = pages.entrySet().iterator().next().getValue();
		
		initHistory();
		
		initPage();
		
		if(startPage instanceof ChartPage) {
			ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART, ChartPackage.CONTROLS);
	        chartLoader.loadApi(new Runnable() {
	            @Override
	            public void run() {
	            	ChartPage page = (ChartPage)startPage;
	            	page.initPage();
	            	showPage(page);
	            }
	        });
		} else {
			showPage((AdminPage)startPage);
		}
		
		initWidget(wrapper);
	}
	
	private void initHistory() {
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
			
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
            	final String pageName = event.getValue();
            	if(pages.containsKey(pageName)) {
            		Page page = pages.get(pageName);
            		if(page instanceof ChartPage)
                		showPage((ChartPage)page);
            		else
                		showPage((AdminPage)page);
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
	
	private void showPage(Widget page)
	{
		content.clear();
		content.add(page);
	}
}
