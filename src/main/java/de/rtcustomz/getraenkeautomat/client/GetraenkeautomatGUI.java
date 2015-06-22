package de.rtcustomz.getraenkeautomat.client;

import java.util.TreeMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class GetraenkeautomatGUI implements EntryPoint {

	FlowPanel content = new FlowPanel();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		TreeMap<String, Page> pages = new TreeMap<>();
		pages.put(EvalChartSlotPage.getPageName(),EvalChartSlotPage.getInstance());
		
		MainLayout mainLayout = new MainLayout(pages);
		
		RootPanel.get().add(mainLayout);
	}
}
