package api.v1;

import api.Response.CustomResponses;
import api.interfaces.IEvent;
import api.models.Event;
import api.services.EventService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/events")
public class EventResources implements IEvent {

    EventService eventService = new EventService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response create(Event event) {
        try {
            return eventService.create(event);
        } catch (Exception e) {
            return CustomResponses.serverError(e.getMessage());
        }
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response read(@PathParam("id") int id) {
        try {
            return eventService.read(id);
        } catch (Exception e) {
            return CustomResponses.serverError(e.getMessage());
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response update(Event event) {
        try {
            return eventService.update(event);
        } catch (Exception e) {
            return CustomResponses.serverError(e.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response delete(@PathParam("id") int id) {
        try {
            return eventService.delete(id);
        } catch (Exception e) {
            return CustomResponses.serverError(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response getEvents(@QueryParam("major") int major_id, @QueryParam("club") int club_id) {
        try {
            return eventService.getEvents(major_id, club_id);
        } catch (Exception e) {
            return CustomResponses.serverError(e.getMessage());
        }
    }
}
