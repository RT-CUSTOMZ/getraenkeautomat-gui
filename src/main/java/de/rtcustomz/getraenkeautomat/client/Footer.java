package de.rtcustomz.getraenkeautomat.client;

import com.google.gwt.dom.client.BRElement;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;

public class Footer extends Composite {
	public Footer() {
		FlowPanel mainPanel = new FlowPanel();
		
		FlowPanel kontaktPanel = new FlowPanel();
		FlowPanel linkPanel = new FlowPanel();
		
		HTMLPanel kontaktHeader = new HTMLPanel(HeadingElement.TAG_H3, "Kontakt");
		kontaktHeader.setStyleName("title");
		
		FlowPanel kontaktDaten = new FlowPanel(ParagraphElement.TAG);
		kontaktDaten.add(new InlineLabel("Westfälische Hochschule Campus Bocholt"));
		kontaktDaten.add(new FlowPanel(BRElement.TAG));
		kontaktDaten.add(new InlineLabel("Dennis Ahrens, Fabian Kalkofen"));
		kontaktDaten.add(new FlowPanel(BRElement.TAG));
		kontaktDaten.add(new InlineLabel("Münsterstrasse 265"));
		kontaktDaten.add(new FlowPanel(BRElement.TAG));
		kontaktDaten.add(new InlineLabel("46397 Bocholt"));
		
		kontaktPanel.add(kontaktHeader);
		kontaktPanel.add(kontaktDaten);
		kontaktPanel.setStyleName("kontakt");
		
		HTMLPanel linkHeader = new HTMLPanel(HeadingElement.TAG_H3, "Links");
		linkHeader.setStyleName("title");
		
		FlowPanel links = new FlowPanel(UListElement.TAG);
		
		FlowPanel admin = new FlowPanel(LIElement.TAG);
		admin.add(new Anchor("Admin", "AdminGUI.html"));
		
		FlowPanel rtcustomz = new FlowPanel(LIElement.TAG);
		rtcustomz.add(new Anchor("42volt.de", "http://42volt.de"));
		
		FlowPanel slab = new FlowPanel(LIElement.TAG);
		slab.add(new Anchor("Student's Lab", "http://www.studentslab.de"));
		
		links.add(admin);
		links.add(rtcustomz);
		links.add(slab);
		
		linkPanel.add(linkHeader);
		linkPanel.add(links);
		linkPanel.setStyleName("links");
		
		mainPanel.add(kontaktPanel);
		mainPanel.add(linkPanel);
		
		initWidget(mainPanel);
		getElement().setId("footer");
	}
}
