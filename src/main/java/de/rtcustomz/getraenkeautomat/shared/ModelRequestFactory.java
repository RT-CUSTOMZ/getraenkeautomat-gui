package de.rtcustomz.getraenkeautomat.shared;

import com.google.web.bindery.requestfactory.shared.RequestFactory;

import de.rtcustomz.getraenkeautomat.shared.requests.CardRequest;
import de.rtcustomz.getraenkeautomat.shared.requests.HistoryRequest;
import de.rtcustomz.getraenkeautomat.shared.requests.SlotRequest;
import de.rtcustomz.getraenkeautomat.shared.requests.UserRequest;

public interface ModelRequestFactory extends RequestFactory {
	CardRequest cardRequest();
	
	UserRequest userRequest();
	
	HistoryRequest historyRequest();
	
	SlotRequest slotRequest();
}
