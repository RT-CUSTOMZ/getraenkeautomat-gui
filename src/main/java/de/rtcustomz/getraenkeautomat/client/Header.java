package de.rtcustomz.getraenkeautomat.client;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;

// TODO: use HeaderPanel instead? => http://www.gwtproject.org/javadoc/latest/com/google/gwt/user/client/ui/HeaderPanel.html
public class Header extends Composite {
	public Header() {
		FlowPanel mainPanel = new FlowPanel();
		FlowPanel subHeader = new FlowPanel();
		FlowPanel headerWrap = new FlowPanel();
		
		subHeader.addStyleName("subheader");
		subHeader.add(new HTML("<h1 class='description'>Die Anlaufstelle für Projekte am Campus Bocholt</h1>"));
		
		headerWrap.addStyleName("header_wrap");
		
		Image logo = new Image(MyResources.INSTANCE.logo());
		logo.setAltText("42volt.de");
		
		Anchor image = new Anchor("", "http://42volt.de");
		image.setHTML(logo.getElement().getString());
		
		Anchor rtzeit = new Anchor("RT Zeit", "http://42volt.de/?page_id=34");
		rtzeit.getElement().setId("RTZEIT");
		
		headerWrap.add(image);
		headerWrap.add(rtzeit);
		
		mainPanel.add(subHeader);
		mainPanel.add(headerWrap);
		
		initWidget(mainPanel);
		getElement().setId("header");
	}
}
