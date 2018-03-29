package de.rtcustomz.getraenkeautomat.client.charts;

import java.util.Map;

import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;

import de.rtcustomz.getraenkeautomat.client.Page;

public abstract class ChartPage extends Composite implements Page, ResizeHandler {
	public FlowPanel page = new FlowPanel();
	public abstract void drawChart();
	public abstract void redrawChart();
	public abstract void initData();
	public abstract void setMode(String mode);	// TODO: add converter from string to mode
	public abstract void setFilter(Map<String, String> filter);
	
	public final ListBox modeSelect = new ListBox();
	public final ListBox monthSelect = new ListBox();
	public ListBox yearSelect = new ListBox();
	
	// TODO: get these from history
	public static final int MINYEAR = 2013;
	public static final int MAXYEAR = 2018;
	
	// TODO: get these from history
	public static final int MINMONTH = 1;
	public static final int MAXMONTH = 12;
	
	public static final int MINDAY = 1;
	
	
    public void changeHistory(String key, String value) {
		String newToken;
		
		final String appendToken = key + "=" + value;

		// replace mode in history if present
		if(History.getToken().contains(key)) {
			StringBuilder tokens = new StringBuilder();
			
			int i = 0;
        	
        	for(String token : History.getToken().split("&")) {
        		if(i != 0) {
        			tokens.append("&");
        		}
        		if(token.contains(key)) {
        			tokens.append(appendToken);
        		} else {
        			tokens.append(token);
        		}
        		i++;
        	}
        	
        	newToken = tokens.toString();
		} else {
			newToken = History.getToken() + "&" + appendToken;
		}
    	
		History.replaceItem(newToken, false);
	}

	native void console( String message) /*-{
	    console.log( message );
	}-*/;
}
