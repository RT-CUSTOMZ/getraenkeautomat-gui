package de.rtcustomz.getraenkeautomat.client;

import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class Navigation extends Composite {
	public Navigation (String... pages) {
		FlowPanel mainPanel = new FlowPanel();
		
		FlowPanel nav = new FlowPanel(UListElement.TAG);
		
//		FlowPanel projekte = new FlowPanel(LIElement.TAG);
//		projekte.add(new Anchor("Projekte"));
//		
//		FlowPanel impressum = new FlowPanel(LIElement.TAG);
//		impressum.add(new Anchor("Impressum"));
//		
//		nav.add(projekte);
//		nav.add(impressum);
		
		for(final String page : pages)
		{
			FlowPanel nextpage = new FlowPanel(LIElement.TAG);
			Anchor anchor = new Anchor(page);
			anchor.addClickHandler(new ClickHandler() {
	            @Override
	            public void onClick(ClickEvent event) {
	                History.newItem(page, true);
	            }
	        });
			nextpage.add(anchor);
			nav.add(nextpage);
		}
		
		mainPanel.add(nav);
		
		initWidget(mainPanel);
		getElement().setId("navigation");
	}
}
