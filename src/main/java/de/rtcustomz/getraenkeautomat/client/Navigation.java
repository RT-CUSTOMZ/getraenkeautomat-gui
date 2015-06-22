package de.rtcustomz.getraenkeautomat.client;

import java.util.Set;

import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class Navigation extends Composite {
	public Navigation (Set<String> pages) {
		FlowPanel mainPanel = new FlowPanel();
		
		FlowPanel nav = new FlowPanel(UListElement.TAG);
		
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
