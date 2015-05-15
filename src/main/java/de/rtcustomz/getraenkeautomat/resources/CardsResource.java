package de.rtcustomz.getraenkeautomat.resources;

import java.util.HashMap;
import java.util.Map;

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

@Singleton
@Path("cards")
public class CardsResource {
	@Context
	UriInfo uriInfo;
	
	Map<String, Card> cards;

	public CardsResource() {
		cards = new HashMap<String, Card>();
	}

	@PUT
	@Path("/{id}")
	public Response createCard(@PathParam("id") String id, @QueryParam("type") String type) {
		if(type == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		if(cards.containsKey(id))
			return Response.noContent().build();
		else {
			Card card = new Card(id, type);
			cards.put(id, card);
			return Response.created(uriInfo.getAbsolutePath()).build();
		}
		// TODO: save card in database
		
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Card getCard(@PathParam("id") String id) {
		if(cards.containsKey(id))
			return cards.get(id);
		else
			throw new WebApplicationException(404);
		// TODO: load card from database
	}
}
