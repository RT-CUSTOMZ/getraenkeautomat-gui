package de.rtcustomz.getraenkeautomat.client;

import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;

public class Header extends Composite {	
	public Header() {
		FlowPanel mainPanel = new FlowPanel();
		FlowPanel subHeader = new FlowPanel();
		FlowPanel headerWrap = new FlowPanel();
		
		subHeader.addStyleName("subheader");
		
		HTMLPanel description = new HTMLPanel(HeadingElement.TAG_H1, "Die Anlaufstelle für Projekte am Campus Bocholt");
		description.setStyleName("description");
		
		subHeader.add(description);
		
		headerWrap.addStyleName("header_wrap");
		
		Image logo = new Image(MyResources.INSTANCE.logo());
		logo.setAltText("42volt.de");
		
		Anchor image = new Anchor("", "http://42volt.de");
		image.setHTML(logo.getElement().getString());
		
		RTClock rtclock = new RTClock("RTZEIT");
		
		headerWrap.add(image);
		headerWrap.add(rtclock);
		
		mainPanel.add(subHeader);
		mainPanel.add(headerWrap);
		
		initWidget(mainPanel);
		getElement().setId("header");
	}
}
