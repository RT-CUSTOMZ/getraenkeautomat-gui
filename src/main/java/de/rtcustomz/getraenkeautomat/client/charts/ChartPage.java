package de.rtcustomz.getraenkeautomat.client.charts;

import java.util.Map;

import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

import de.rtcustomz.getraenkeautomat.client.Page;

public abstract class ChartPage extends Composite implements Page, ResizeHandler {
	public FlowPanel page = new FlowPanel();
	public abstract void drawChart();
	public abstract void redrawChart();
	public abstract void initData();
	public abstract void setMode(String mode);	// TODO: add converter from string to mode
	public abstract void setFilter(Map<String, String> filter);
	

	native void console( String message) /*-{
	    console.log( message );
	}-*/;
}
