package de.rtcustomz.getraenkeautomat.client;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainLayout extends Composite {
	FlowPanel content = new FlowPanel();
	FlowPanel wrapper = new FlowPanel();
	
	MainLayout(TreeMap<String, Page> pages) {
		initPage(pages.keySet());
		initHistory(pages);
		initWidget(wrapper);
	}
	
	private void initHistory(final TreeMap<String, Page> pages) {
		String initToken = History.getToken();
        
        if (initToken.length() == 0) {
          History.newItem(pages.firstKey());
        }
		
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
			
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
            	for (Map.Entry<String, Page> entry : pages.entrySet()) {
            		if (event.getValue().equals(entry.getKey())){
            			showPage(entry.getValue());
                    }
            	} 
            }
		});
        History.fireCurrentHistoryState();
	}
	
	private void initPage(Set<String> pages) {
		MyResources.INSTANCE.css().ensureInjected();
		
		FlowPanel clear = new FlowPanel();
		clear.setStyleName("clear");

		Header header = new Header();
		Navigation nav = new Navigation(pages);
		Footer footer = new Footer();

		wrapper.getElement().setId("wrapper");
    	wrapper.add(header);
    	wrapper.add(nav);
    	wrapper.add(clear);
    	
    	content.getElement().setId("content");
    	
    	content.add(new HTMLPanel(HeadingElement.TAG_H1, "Inhalt wird geladen ..."));
    	
    	wrapper.add(content);
    	wrapper.add(footer);
	}
	
	private void showPage(Widget page)
	{
		content.clear();
		content.add(page);
	}
}
