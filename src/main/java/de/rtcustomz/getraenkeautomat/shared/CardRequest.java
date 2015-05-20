package de.rtcustomz.getraenkeautomat.shared;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

import de.rtcustomz.getraenkeautomat.model.Card;

@Service(Card.class)
public interface CardRequest extends RequestContext {
	Request<Long> countCards();
	
	Request<List<CardProxy>> findAllCards();
	
	Request<List<CardProxy>> findCardEntries(int firstResult, int maxResults);
	
	Request<CardProxy> findCard(int id);
	
	InstanceRequest<CardProxy, Void> persist();
	
	InstanceRequest<CardProxy, Void> create();
	
	InstanceRequest<CardProxy, Void> edit();

	InstanceRequest<CardProxy, Void> remove();
}
