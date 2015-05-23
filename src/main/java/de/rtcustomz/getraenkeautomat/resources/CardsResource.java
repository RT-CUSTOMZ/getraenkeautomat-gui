package de.rtcustomz.getraenkeautomat.resources;

import javax.inject.Singleton;
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
import de.rtcustomz.getraenkeautomat.server.CardDAO;

@Singleton
@Path("cards")
public class CardsResource {
	@Context
	UriInfo uriInfo;

	@PUT
	@Path("/{id}")
	public Response createCard(@PathParam("id") String id, @QueryParam("type") String type) {
		if(id == null || type == null)
			return Response.status(Status.BAD_REQUEST).build();

		if(CardDAO.findById(id) != null)
			return Response.noContent().build();
		else {
			try {
				CardDAO.createCard(id, type);
			} catch (Exception e) {
				throw new WebApplicationException(500);
			}

			return Response.created(uriInfo.getAbsolutePath()).build();
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Card getCard(@PathParam("id") String id) {
		Card card = CardDAO.findById(id);
		
		if(card != null)
			return card;
		else
			throw new WebApplicationException(404);
	}
}
