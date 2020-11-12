package api.v1;

import api.Response.CustomResponses;
import api.interfaces.IStudent;
import api.models.Student;
import api.services.StudentService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("students")
public class StudentResources implements IStudent {

    StudentService studentService = new StudentService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response create(Student student) {
        try {
            return studentService.create(student);
        } catch (Exception e) {
            return CustomResponses.serverError(e.getMessage());
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response read(@PathParam("id") int id) {
        try {
            return studentService.read(id);
        } catch (Exception e) {
            return CustomResponses.serverError(e.getMessage());
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response update(Student student) {
        try {
            return studentService.update(student);
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
            return studentService.delete(id);
        } catch (Exception e) {
            return CustomResponses.serverError(e.getMessage());
        }
    }

    @GET
    @Override
    public Response getStudents(
            @QueryParam("group") int group_id,
            @QueryParam("major") int major_id,
            @QueryParam("year") int year,
            @QueryParam("name") String name,
            @QueryParam("email") String email) {
        try {
            return studentService.getStudents(group_id, major_id, year, name, email);
        } catch (Exception e) {
            return CustomResponses.serverError(e.getMessage());
        }
    }
}
