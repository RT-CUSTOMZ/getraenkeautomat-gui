package de.rtcustomz.getraenkeautomat.client;

import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

public class Navigation extends Composite {
	public Navigation () {
		FlowPanel mainPanel = new FlowPanel();
		
		FlowPanel nav = new FlowPanel(UListElement.TAG);
		
		for(int i=0; i<3; i++) {
			FlowPanel li = new FlowPanel(LIElement.TAG);
			li.add(new HTML("li nr" + (i+1)));
			
			nav.add(li);
		}
		
//		UListElement list  = Document.get().createULElement();
//		Element li = Document.get().createLIElement().cast(); 
//        list.appendChild(li); 
		
		mainPanel.add(nav);
		
		initWidget(mainPanel);
		getElement().setId("navigation");
	}
}
