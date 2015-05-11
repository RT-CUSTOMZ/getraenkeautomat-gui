package de.rtcustomz.getraenkeautomat.resources;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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
}
