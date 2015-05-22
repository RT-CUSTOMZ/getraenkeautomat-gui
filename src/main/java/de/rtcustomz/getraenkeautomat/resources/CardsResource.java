package de.rtcustomz.getraenkeautomat.resources;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
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
	
	@Resource
	UserTransaction utx;
	
	EntityManager em;
	
	@PostConstruct
	public void init() {
		em = DatabaseController.createEntityManager();
	}
	
	@PreDestroy
	public void destroy() {
		em.close();
	}

	@PUT
	@Path("/{id}")
	public Response createCard(@PathParam("id") String id, @QueryParam("type") String type) {
		if(type == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		try {
			if(em.find(Card.class, id) != null)
				return Response.noContent().build();
			else {
				utx.begin();
				em.joinTransaction();
				Card card = new Card(id, type);
				em.persist(card);
				em.flush();
				utx.commit();
				
				return Response.created(uriInfo.getAbsolutePath()).build();
			}
		} catch (Exception e) {
			try {
				utx.rollback();
			} catch (Exception ignore) {}
			throw new WebApplicationException(500);
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Card getCard(@PathParam("id") String id) {
		Card card = null;
		
		card = em.find(Card.class, id);
		
		if(card != null)
			return card;
		else
			throw new WebApplicationException(404);
	}
}
