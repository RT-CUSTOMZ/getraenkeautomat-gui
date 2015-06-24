package de.rtcustomz.getraenkeautomat.client.admin;

import java.util.LinkedHashMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import de.rtcustomz.getraenkeautomat.client.MainLayout;
import de.rtcustomz.getraenkeautomat.client.Page;

public class AdminGUI implements EntryPoint {
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
	LinkedHashMap<String, Page> pages = new LinkedHashMap<>();
	pages.put(AdminCardPage.getPageName(), AdminCardPage.getInstance());
	pages.put(AdminUserPage.getPageName(), AdminUserPage.getInstance());
	pages.put(AdminSlotPage.getPageName(), AdminSlotPage.getInstance());

	MainLayout mainLayout = new MainLayout(pages);

	RootPanel.get().add(mainLayout);
    }
}
