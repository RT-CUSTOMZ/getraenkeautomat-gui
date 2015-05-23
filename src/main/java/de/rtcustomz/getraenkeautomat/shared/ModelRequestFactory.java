package de.rtcustomz.getraenkeautomat.shared;

import com.google.web.bindery.requestfactory.shared.RequestFactory;

public interface ModelRequestFactory extends RequestFactory {
	CardRequest cardRequest();
	
	UserRequest userRequest();
	
	HistoryRequest historyRequest();
	
	SlotRequest slotRequest();
}
