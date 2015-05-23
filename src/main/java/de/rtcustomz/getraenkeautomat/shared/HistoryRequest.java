package de.rtcustomz.getraenkeautomat.shared;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

import de.rtcustomz.getraenkeautomat.client.CardProxy;
import de.rtcustomz.getraenkeautomat.client.HistoryEntryProxy;
import de.rtcustomz.getraenkeautomat.client.SlotProxy;
import de.rtcustomz.getraenkeautomat.server.HistoryEntryDAO;

@Service(HistoryEntryDAO.class)
public interface HistoryRequest extends RequestContext {
	Request<Long> countHistoryEntries();
	
	Request<List<HistoryEntryProxy>> findAllHistoryEntries();
	
	Request<List<HistoryEntryProxy>> findHistoryEntries(int firstResult, int maxResults);
	
	Request<HistoryEntryProxy> findById(Long id);
	
	Request<Void> save(HistoryEntryProxy history);
	
	Request<HistoryEntryProxy> createHistoryEntry(CardProxy card, SlotProxy slot);

	Request<Void> delete(HistoryEntryProxy history);
}
