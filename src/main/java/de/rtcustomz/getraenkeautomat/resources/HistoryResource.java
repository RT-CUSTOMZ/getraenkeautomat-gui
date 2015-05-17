package de.rtcustomz.getraenkeautomat.resources;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import de.rtcustomz.getraenkeautomat.model.HistoryEntry;

@Singleton
@Path("history")
public class HistoryResource {
	@Context
	UriInfo uriInfo;
	
	Map<Integer, HistoryEntry> history;

	public HistoryResource() {
		this.history = new HashMap<Integer, HistoryEntry>();
	}
	
	// TODO: modify createHistoryEntry
	@POST
	@Path("/create")
	public Response createHistoryEntry(@QueryParam("card_id") String card_id, @QueryParam("slot") Integer slot) {
		if(card_id == null || slot == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		HistoryEntry historyEntry = new HistoryEntry();//(card_id, slot);
		history.put(history.size()+1, historyEntry);
		
		URI location = uriInfo.getAbsolutePath();
		
		return Response.created(URI.create(location.getPath()+"/"+history.size())).build();
		// TODO: save new history entry in database
		
	}

}
