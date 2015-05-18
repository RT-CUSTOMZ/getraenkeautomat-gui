package de.rtcustomz.getraenkeautomat.resources;

import java.net.URI;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import de.rtcustomz.getraenkeautomat.model.Card;
import de.rtcustomz.getraenkeautomat.model.HistoryEntry;
import de.rtcustomz.getraenkeautomat.model.Slot;
import de.rtcustomz.getraenkeautomat.util.DatabaseController;

@Singleton
@Path("history")
public class HistoryResource {
	@Context
	UriInfo uriInfo;

	@POST
	@Path("/create")
	public Response createHistoryEntry(@QueryParam("card_id") String card_id, @QueryParam("slot") Integer slot_id) {
		EntityManager em;
		
		if(card_id == null || slot_id == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		em = DatabaseController.createEntityManager();
		
		try {
			Card card = em.find(Card.class, card_id);
			Slot slot = em.find(Slot.class, slot_id);
			
			if(card == null || slot == null) {
				return Response.status(Status.BAD_REQUEST).build();
			}
			
			em.getTransaction().begin();
			HistoryEntry historyEntry = new HistoryEntry(card, slot);
			em.persist(historyEntry);
			em.flush();
			em.getTransaction().commit();
			
			URI location = uriInfo.getAbsolutePath();
			
			return Response.created(URI.create(location.getPath()+"/"+historyEntry.getId())).build();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
		
	}

}
