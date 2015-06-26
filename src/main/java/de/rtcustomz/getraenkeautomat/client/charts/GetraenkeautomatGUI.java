package de.rtcustomz.getraenkeautomat.client.charts;

import java.util.LinkedHashMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

import de.rtcustomz.getraenkeautomat.client.MainLayout;
import de.rtcustomz.getraenkeautomat.client.Page;

public class GetraenkeautomatGUI implements EntryPoint {

	FlowPanel content = new FlowPanel();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		LinkedHashMap<String, Page> pages = new LinkedHashMap<>();
		pages.put(PieChartPage.getPageName(),PieChartPage.getInstance());
		pages.put(ColumnChartPage.getPageName(),ColumnChartPage.getInstance());
		pages.put(LineChartPage.getPageName(),LineChartPage.getInstance());
		
		MainLayout mainLayout = new MainLayout(pages);
		
		RootPanel.get().add(mainLayout);
	}
}
