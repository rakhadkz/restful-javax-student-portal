package api.v1;

import api.Response.CustomResponses;
import api.interfaces.IClub;
import api.models.Club;
import api.services.ClubService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/clubs")
public class ClubResources implements IClub {

    ClubService clubService = new ClubService();
    // /clubs POST
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response create(Club club) {
        try {
            return clubService.create(club);
        } catch (Exception e) {
            return CustomResponses.serverError(e.getMessage());
        }
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response read(@PathParam("id") int id){
        try {
            return clubService.read(id);
        } catch (Exception e) {
            return CustomResponses.serverError(e.getMessage());
        }
    }

    // /clubs PUT
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response update(Club club){
        try {
            return clubService.update(club);
        } catch (Exception e) {
            return CustomResponses.serverError(e.getMessage());
        }
    }

    // /clubs/12341234
    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response delete(@PathParam("id") int id) {
        try {
            return clubService.delete(id);
        } catch (Exception e) {
            return CustomResponses.serverError(e.getMessage());
        }
    }

    // /clubs GET
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response getClubs(){
        try {
            return clubService.getClubs();
        } catch (Exception e) {
            return CustomResponses.serverError(e.getMessage());
        }
    }

    // /clubs/enter POST
    @Path("/enter")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Override
    public Response enter(@FormParam("club_id") int club_id, @FormParam("student_id") int student_id) {
        try {
            return clubService.enter(club_id, student_id);
        } catch (Exception e) {
            return CustomResponses.serverError(e.getMessage());
        }
    }


    @Path("/leave")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Override
    public Response leave(@FormParam("club_id") int club_id, @FormParam("student_id") int student_id) {
        try {
            return clubService.leave(club_id, student_id);
        } catch (Exception e) {
            return CustomResponses.serverError(e.getMessage());
        }
    }

    @Path("/{id}/students")
    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response getStudentsOfClub(@PathParam("id") int id) {
        try {
            return clubService.getStudentsOfClub(id);
        } catch (Exception e) {
            return CustomResponses.serverError(e.getMessage());
        }
    }
}
