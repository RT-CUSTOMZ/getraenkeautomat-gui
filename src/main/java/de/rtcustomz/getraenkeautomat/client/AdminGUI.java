package de.rtcustomz.getraenkeautomat.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class AdminGUI implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
        String initToken = History.getToken();
        
        if (initToken.length() == 0) {
          History.newItem(AdminCardPage.getPageName());
        }
        
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
			
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                System.out.println("Current State : " + event.getValue());
                
                if (event.getValue().equals(AdminCardPage.getPageName())){
                    RootPanel.get().clear();
                    showPage(AdminCardPage.getInstance());
                }
                
                else if (event.getValue().equals(AdminUserPage.getPageName())){
                    RootPanel.get().clear();
                    showPage(AdminUserPage.getInstance());;
                }    
            }
		});
        History.fireCurrentHistoryState(); 
	}
	
	private void showPage(Composite page)
	{
		MyResources.INSTANCE.css().ensureInjected();
		
		FlowPanel wrapper = new FlowPanel();
		FlowPanel clear = new FlowPanel();
		clear.setStyleName("clear");

		Header header = new Header();
		Navigation nav = new Navigation(AdminCardPage.getPageName(),AdminUserPage.getPageName());
		Footer footer = new Footer();

		wrapper.getElement().setId("wrapper");
    	wrapper.add(header);
    	wrapper.add(nav);
    	wrapper.add(clear);
    	
    	FlowPanel content = new FlowPanel();
    	content.getElement().setId("content");
    	
    	// TODO: add content to contentFlowPanel, e.g.:
    	//content.add(new HTMLPanel(HeadingElement.TAG_H1, "Hier kommt der Content rein!!!"));
    	content.add(page);
    	
    	wrapper.add(content);
    	wrapper.add(footer);
    	
    	RootPanel.get().add(wrapper);
	}
}
