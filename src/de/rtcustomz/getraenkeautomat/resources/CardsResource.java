package de.rtcustomz.getraenkeautomat.resources;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.rtcustomz.getraenkeautomat.model.Card;

@Path("cards")
public class CardsResource {
	@Context
	UriInfo uriInfo;

	@PUT
	@Path("/{id}")
	public Response createCard(@PathParam("id") String id) {
		// TODO: create card with no correspondent user, response with Response.created(uriInfo.getAbsolutePath()).build();
		// if card already exists, do nothing: Response.noContent().build()
		return Response.noContent().build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Card getCard(@PathParam("id") String id) {
		return new Card("21436", "42", "testkarte", 1);
	}
}
