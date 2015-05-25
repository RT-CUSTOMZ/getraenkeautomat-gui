package de.rtcustomz.getraenkeautomat.shared.requests;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

import de.rtcustomz.getraenkeautomat.client.proxies.CardProxy;
import de.rtcustomz.getraenkeautomat.server.daos.CardDAO;

@Service(CardDAO.class)
public interface CardRequest extends RequestContext {
	Request<Long> countCards();
	
	Request<List<CardProxy>> findAllCards();
	
	Request<List<CardProxy>> findCardEntries(int firstResult, int maxResults);
	
	Request<CardProxy> findById(String id);
	
	Request<Void> save(CardProxy card);
	
	Request<CardProxy> createCard(String id, String type);

	Request<Void> delete(CardProxy card);
}
