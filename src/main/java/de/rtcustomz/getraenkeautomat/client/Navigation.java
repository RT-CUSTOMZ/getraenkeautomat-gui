package de.rtcustomz.getraenkeautomat.client;

import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class Navigation extends Composite {
	public Navigation () {
		FlowPanel mainPanel = new FlowPanel();
		
		FlowPanel nav = new FlowPanel(UListElement.TAG);
		
		FlowPanel projekte = new FlowPanel(LIElement.TAG);
		projekte.add(new Anchor("Projekte"));
		
		FlowPanel impressum = new FlowPanel(LIElement.TAG);
		impressum.add(new Anchor("Impressum"));
		
		nav.add(projekte);
		nav.add(impressum);
		
		mainPanel.add(nav);
		
		initWidget(mainPanel);
		getElement().setId("navigation");
	}
}
