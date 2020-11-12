package api.Response;

import api.models.ResponseModel;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class CustomResponses {

    public static final Response CREATED = Response.ok(
            new ResponseModel(ResponseMessage.CREATED),
            MediaType.APPLICATION_JSON
    ).build();

    public static final Response UPDATED = Response.ok(
            new ResponseModel(ResponseMessage.UPDATED),
            MediaType.APPLICATION_JSON
    ).build();

    public static final Response DELETED = Response.ok(
            new ResponseModel(ResponseMessage.DELETED),
            MediaType.APPLICATION_JSON
    ).build();

    public static final Response ENTERED = Response.ok(
            new ResponseModel(ResponseMessage.ENTERED),
            MediaType.APPLICATION_JSON
    ).build();

    public static final Response LEFT = Response.ok(
            new ResponseModel(ResponseMessage.LEFT),
            MediaType.APPLICATION_JSON
    ).build();

    public static Response read(Object object){
        return Response.ok(
                object,
                MediaType.APPLICATION_JSON
        ).build();
    }

    public static Response serverError(String message){
        return Response.status(500).entity(new ResponseModel(message)).type(MediaType.APPLICATION_JSON).build();
    }

}
