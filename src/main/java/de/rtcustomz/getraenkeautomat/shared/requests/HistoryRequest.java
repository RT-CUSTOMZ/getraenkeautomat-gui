package de.rtcustomz.getraenkeautomat.shared.requests;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

import de.rtcustomz.getraenkeautomat.client.proxies.CardProxy;
import de.rtcustomz.getraenkeautomat.client.proxies.HistoryEntryProxy;
import de.rtcustomz.getraenkeautomat.client.proxies.PieChartDataProxy;
import de.rtcustomz.getraenkeautomat.client.proxies.SlotProxy;
import de.rtcustomz.getraenkeautomat.server.daos.HistoryEntryDAO;

@Service(HistoryEntryDAO.class)
public interface HistoryRequest extends RequestContext {
	Request<Long> countHistoryEntries();
	
	Request<List<HistoryEntryProxy>> findAllHistoryEntries();
	
	Request<List<HistoryEntryProxy>> findHistoryEntries(int firstResult, int maxResults);
	
	Request<HistoryEntryProxy> findById(Long id);
	
	Request<Void> save(HistoryEntryProxy history);
	
	Request<HistoryEntryProxy> createHistoryEntry(CardProxy card, SlotProxy slot);

	Request<Void> delete(HistoryEntryProxy history);
	
	Request<List<PieChartDataProxy>> getPieChartData(int month, int year);
	
//	Request<Map<String, Integer>> getPieChartData(int month, int year);
//	Request<List<String>> getPieChartData(int month, int year);
}
