package de.rtcustomz.getraenkeautomat.resources;

import java.net.URI;

import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import de.rtcustomz.getraenkeautomat.model.Card;
import de.rtcustomz.getraenkeautomat.model.HistoryEntry;
import de.rtcustomz.getraenkeautomat.model.Slot;
import de.rtcustomz.getraenkeautomat.server.CardDAO;
import de.rtcustomz.getraenkeautomat.server.HistoryEntryDAO;
import de.rtcustomz.getraenkeautomat.server.SlotDAO;

@Singleton
@Path("history")
public class HistoryResource {
	@Context
	UriInfo uriInfo;

	@POST
	@Path("/create")
	public Response createHistoryEntry(@QueryParam("card_id") String card_id, @QueryParam("slot") Integer slot_id) {
		Card card = CardDAO.findById(card_id);
		Slot slot = SlotDAO.findById(slot_id);
		
		if(card == null || slot == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		HistoryEntry entry;
		try {
			entry = HistoryEntryDAO.createHistoryEntry(card, slot);
		} catch (Exception e) {
			throw new WebApplicationException(500);
		}
		
		URI location = uriInfo.getAbsolutePath();
		
		return Response.created(URI.create(location.getPath()+"/"+entry.getId())).build();
		
	}

}
