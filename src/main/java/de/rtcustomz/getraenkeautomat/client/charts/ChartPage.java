package de.rtcustomz.getraenkeautomat.client.charts;

import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

import de.rtcustomz.getraenkeautomat.client.Page;

public abstract class ChartPage extends Composite implements Page, ResizeHandler {
	public FlowPanel page = new FlowPanel();
	abstract void drawChart();
	

	native void console( Object message) /*-{
	    console.log( message );
	}-*/;
}
