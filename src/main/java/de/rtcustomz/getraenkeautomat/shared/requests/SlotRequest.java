package de.rtcustomz.getraenkeautomat.shared.requests;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

import de.rtcustomz.getraenkeautomat.client.proxies.SlotProxy;
import de.rtcustomz.getraenkeautomat.server.daos.SlotDAO;

@Service(SlotDAO.class)
public interface SlotRequest extends RequestContext {
	Request<Long> countSlots();
	
	Request<List<SlotProxy>> findAllSlots();
	
	Request<List<SlotProxy>> findSlotEntries(int firstResult, int maxResults);
	
	Request<SlotProxy> findById(Integer id);
	
	Request<Void> save(SlotProxy slot);
	
	Request<SlotProxy> createSlot(Integer id, String type);

	Request<Void> delete(SlotProxy slot);
}
