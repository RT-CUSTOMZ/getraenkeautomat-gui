package de.rtcustomz.getraenkeautomat.client;

import java.util.LinkedHashMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class AdminGUI implements EntryPoint {
	
//	FlowPanel content = new FlowPanel();
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		LinkedHashMap<String, Page> pages = new LinkedHashMap<>();
		pages.put(AdminCardPage.getPageName(),AdminCardPage.getInstance());
		pages.put(AdminUserPage.getPageName(),AdminUserPage.getInstance());
		
		MainLayout mainLayout = new MainLayout(pages);
		
		RootPanel.get().add(mainLayout);
	}
}
