package api.v1;

import api.Response.CustomResponses;
import api.interfaces.INews;
import api.models.News;
import api.services.NewsService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("news")
public class NewsResources implements INews {

    NewsService newsService = new NewsService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response create(News news){
        try {
            return newsService.create(news);
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
            return newsService.read(id);
        } catch (Exception e) {
            return CustomResponses.serverError(e.getMessage());
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response update(News news) {
        try {
            return newsService.update(news);
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
            return newsService.delete(id);
        } catch (Exception e) {
            return CustomResponses.serverError(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response getNews(@QueryParam("major") int major_id) {
        try {
            return newsService.getNews(major_id);
        } catch (Exception e) {
            return CustomResponses.serverError(e.getMessage());
        }
    }
}
