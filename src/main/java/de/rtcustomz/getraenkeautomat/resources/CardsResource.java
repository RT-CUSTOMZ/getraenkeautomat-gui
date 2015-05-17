package de.rtcustomz.getraenkeautomat.resources;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import de.rtcustomz.getraenkeautomat.model.Card;
import de.rtcustomz.getraenkeautomat.util.DatabaseController;

@Singleton
@Path("cards")
public class CardsResource {
	@Context
	UriInfo uriInfo;
	
//	Map<String, Card> cards;

//	public CardsResource() {
//		cards = new HashMap<String, Card>();
//	}

	@PUT
	@Path("/{id}")
	public Response createCard(@PathParam("id") String id, @QueryParam("type") String type) {
		EntityManager em;
		if(type == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		em = DatabaseController.createEntityManager();
		
		try {
	//		if(cards.containsKey(id))
			if(em.find(Card.class, id) != null)
				return Response.noContent().build();
			else {
				em.getTransaction().begin();
				Card card = new Card(id, type);
	//			cards.put(id, card);
				em.persist(card);
				em.getTransaction().commit();
				
				return Response.created(uriInfo.getAbsolutePath()).build();
			}
		} finally {
			em.close();
		}
		// TODO: save card in database
		
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Card getCard(@PathParam("id") String id) {
		EntityManager em = DatabaseController.createEntityManager();
		Card card = null;
		
		try {
			card = em.find(Card.class, id);
		} finally {
			em.close();
		}
//		if(cards.containsKey(id))
//			return cards.get(id);
		if(card != null)
			return card;
		else
			throw new WebApplicationException(404);
		// TODO: load card from database
	}
}
